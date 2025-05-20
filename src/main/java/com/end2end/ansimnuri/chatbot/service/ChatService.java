package com.end2end.ansimnuri.chatbot.service;

import com.end2end.ansimnuri.chatbot.dao.ChatDAO;
import com.end2end.ansimnuri.chatbot.dto.PoliceDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ChatService {

    private final ChatDAO chatDao;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${openai.api.key}")
    private String apiKey;

    public String askOpenAI(List<Map<String, String>> messages) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        Map<String, Object> body = Map.of(
                "model", "gpt-3.5-turbo",
                "messages", messages
        );

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(
                "https://api.openai.com/v1/chat/completions",
                entity,
                String.class
        );
        return response.getBody();
    }

    public List<PoliceDTO> findPoliceByLocation(String keyword) {
        return chatDao.findPoliceByLocation(keyword).stream()
                .map(dto -> new PoliceDTO(dto.getName(), dto.getAddress()))
                .collect(Collectors.toList());
    }

    public String findGuideAnswer(String question) {
        return chatDao.findGuideAnswer(question);
    }

    public String findSupportAnswer(String question) {
        return chatDao.findSupportAnswer(question);
    }

    public String findFAQAnswer(String question) {
        return chatDao.findFAQAnswer(question);
    }

    public List<Map<String, String>> getTop3News() {
        return chatDao.getTop3News();
    }

    public List<Map<String, String>> summarizeNews(List<Map<String, String>> newsList) {
        List<Map<String, String>> result = new ArrayList<>();
        for (Map<String, String> news : newsList) {
            String title = news.get("TITLE");
            String url = news.get("URL");

            List<Map<String, String>> messages = List.of(
                    Map.of("role", "system", "content", "다음 뉴스의 제목과 URL을 참고해서 기사 내용을 직접 조사해 300자 이내로 한국어로 핵심 내용만 요약해줘."),
                    Map.of("role", "user", "content", "제목: " + title + "\nURL: " + url)
            );

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(apiKey);

            Map<String, Object> body = Map.of(
                    "model", "gpt-3.5-turbo",
                    "messages", messages
            );

            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(body, headers);
            try {
                ResponseEntity<Map> response = restTemplate.postForEntity(
                        "https://api.openai.com/v1/chat/completions",
                        requestEntity,
                        Map.class
                );
                List<Map<String, Object>> choices = (List<Map<String, Object>>) response.getBody().get("choices");
                Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
                String summary = (String) message.get("content");
                result.add(Map.of(
                        "title", title,
                        "summary", summary,
                        "url",url
                ));
            } catch (Exception e) {
                result.add(Map.of(
                        "title", title,
                        "summary", "요약 실패: " + e.getMessage(),
                        "url", url
                ));
            }
        }
        return result;
    }

}