package com.end2end.ansimnuri.news.service;

import com.end2end.ansimnuri.news.dto.NewsDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class NewsServiceImpl implements NewsService {
    @Value("${news.api.key}")
    private String apiKey;
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public List<NewsDTO> fetchNews() {
        List<NewsDTO> result = new ArrayList<>();
        try {
            String url = "https://newsapi.org/v2/everything?q=\"살인\"&apiKey=" + apiKey;

            String response = restTemplate.getForObject(url, String.class);
            System.out.println(response);

            JsonNode root = new ObjectMapper().readTree(response);
            JsonNode articles = root.path("articles");

            for (JsonNode article : articles) {
                NewsDTO newsDTO = NewsDTO.builder()
                        .title(article.path("title").asText())
                        .content(article.path("content").asText())
                        .thumbnailImg(article.path("urlToImage").asText())
                        .regDate(article.path("publishedAt").asText())
                        .url(article.path("url").asText())
                        .build();
                result.add(newsDTO);
                }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("news api와의 연동이 오류되었습니다.", e);
        }
        return result;
    }
}
