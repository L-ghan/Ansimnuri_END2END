package com.end2end.ansimnuri.chatbot.service;

import com.end2end.ansimnuri.chatbot.dao.ChatDao;
import com.end2end.ansimnuri.chatbot.dto.PoliceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ChatService {

    @Autowired
    private ChatDao chatDao;

    @Value("${openai.api.key}")
    private String apiKey;

    @Value("${kakao.api.key}")
    private String kakaoApiKey;

    public String askOpenAI(List<Map<String, String>> messages) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        Map<String, Object> body = new HashMap<>();
        body.put("model", "gpt-3.5-turbo");
        body.put("messages", messages);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(
                "https://api.openai.com/v1/chat/completions",
                entity,
                String.class
        );
        return response.getBody();
    }

    public PoliceDto findPoliceByLocation(String keyword) {
        System.out.println("🔍 입력 키워드: " + keyword);

        double[] coords = getCoordinatesFromKakao(keyword);
        if (coords == null) {
            System.out.println("❌ Kakao에서 좌표 못 받아옴");
            return null;
        }

        double userLat = coords[0];
        double userLng = coords[1];
        System.out.println("📍 변환된 좌표: " + userLat + ", " + userLng);

        List<PoliceDto> allStations = chatDao.findPoliceByLocation();
        System.out.println("📌 전체 경찰서 수: " + allStations.size());

        PoliceDto nearest = null;
        double minDist = Double.MAX_VALUE;

        for (PoliceDto p : allStations) {
            if (p.getLatitude() == null || p.getLongitude() == null) {
                System.out.println("⚠️ " + p.getName() + " → 좌표 없음");
                continue;
            }
            double dist = haversine(userLat, userLng, p.getLatitude(), p.getLongitude());
            System.out.println("📏 거리: " + dist + "km → " + p.getName());

            if (dist < minDist) {
                minDist = dist;
                nearest = p;
            }
        }

        if (nearest == null) {
            System.out.println("❌ 근접 경찰서 없음");
        } else {
            System.out.println("✅ 가장 가까운 경찰서: " + nearest.getName());
        }

        return nearest;
    }

    private double[] getCoordinatesFromKakao(String query) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "KakaoAK " + kakaoApiKey);

            HttpEntity<String> entity = new HttpEntity<>(headers);
            String url = "https://dapi.kakao.com/v2/local/search/keyword.json?query=" + URLEncoder.encode(query, "UTF-8");

            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
            List documents = (List) response.getBody().get("documents");
            if (documents.isEmpty()) return null;

            Map first = (Map) documents.get(0);
            double lat = Double.parseDouble((String) first.get("y"));
            double lng = Double.parseDouble((String) first.get("x"));
            return new double[]{lat, lng};

        } catch (Exception e) {
            System.out.println("❌ Kakao API 오류: " + e.getMessage());
            return null;
        }
    }

    private double haversine(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}
