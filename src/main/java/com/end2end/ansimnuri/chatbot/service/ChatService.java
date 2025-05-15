package com.end2end.ansimnuri.chatbot.service;

import com.end2end.ansimnuri.chatbot.dao.ChatDao;
import com.end2end.ansimnuri.map.domain.repository.PoliceRepository;
import com.end2end.ansimnuri.map.dto.PoliceDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ChatService {

    private final ChatDao chatDao;
    private final PoliceRepository policeRepository;

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

    public List<PoliceDTO> findPoliceByLocation(String keyword) {
        System.out.println("🔍 입력 키워드: " + keyword);

        double[] coords = getCoordinatesFromKakao(keyword);
        if (coords == null) {
            System.out.println("❌ Kakao에서 좌표 못 받아옴");
            return null;
        }

        double userLng = coords[0]; // longitude
        double userLat = coords[1]; // latitude
        System.out.println("📍 변환된 좌표: " + userLat + ", " + userLng);

        List<PoliceDTO> allStations = policeRepository.findAll().stream()
                .map(PoliceDTO::of)
                .filter(p -> Double.compare(p.getLatitude(), 0.0) != 0 && Double.compare(p.getLongitude(), 0.0) != 0)
                .collect(Collectors.toList());

        System.out.println("📌 전체 유효 경찰서 수: " + allStations.size());

        List<PoliceDTO> nearest = allStations.stream()
                .sorted(Comparator.comparingDouble(p -> haversine(userLat, userLng, p.getLatitude(), p.getLongitude())))
                .limit(3)
                .collect(Collectors.toList());

        nearest.forEach(p -> {
            double dist = haversine(userLat, userLng, p.getLatitude(), p.getLongitude());
            System.out.println("📏 거리: " + dist + "km → " + p.getName());
        });

        return nearest;
    }

    private double[] getCoordinatesFromKakao(String query) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            URI uri = UriComponentsBuilder
                    .fromUriString("https://dapi.kakao.com/v2/local/search/address.json")
                    .queryParam("query", query)
                    .encode()
                    .build()
                    .toUri();
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "KakaoAK " + kakaoApiKey);

            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);

            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = mapper.readTree(response.getBody());
            JsonNode documents = jsonNode.path("documents");
            JsonNode target = documents.path(0);

            double longitude = target.path("x").asDouble();
            double latitude = target.path("y").asDouble();

            return new double[]{longitude, latitude};
        } catch (Exception e) {
            System.out.println("❌ Kakao API 오류: " + e.getMessage());
            throw new RuntimeException("Kakao Api 오류 " + e.getMessage());
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
