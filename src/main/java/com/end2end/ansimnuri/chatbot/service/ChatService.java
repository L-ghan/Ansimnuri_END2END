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
        return response.getBody(); // 응답 JSON 문자열
    }

    // 1. 사용자 위치로 경찰서 찾기 (메인 메서드)
    public PoliceDto findPoliceByLocation(String keyword) {
        double[] coords = getCoordinatesFromKakao(keyword);
        if (coords == null) return null;

        double userLat = coords[0];
        double userLng = coords[1];

        List<PoliceDto> allStations = chatDao.findAllPolice();

        PoliceDto nearest = null;
        double minDistance = Double.MAX_VALUE;

        for (PoliceDto station : allStations) {
            if (station.getLatitude() == null || station.getLongitude() == null) continue;

            double dist = haversine(userLat, userLng, station.getLatitude(), station.getLongitude());
            if (dist < minDistance) {
                minDistance = dist;
                nearest = station;
            }
        }

        return nearest;
    }

    // 2. Kakao API 좌표 변환
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
            return null;
        }
    }

    // 3. 거리 계산 메서드 (Haversine 공식)
    private double haversine(double lat1, double lon1, double lat2, double lon2) {
        final int R = 6371; // 지구 반지름 (km)
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }
}