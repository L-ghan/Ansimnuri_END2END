package com.end2end.ansimnuri.chatbot.service;

import com.end2end.ansimnuri.chatbot.dao.ChatDAO;
import com.end2end.ansimnuri.chatbot.dto.PoliceDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ChatService {

    private final ChatDAO chatDao;

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

}
