package com.end2end.ansimnuri.news.service;

import com.end2end.ansimnuri.news.domain.entity.News;
import com.end2end.ansimnuri.news.domain.repository.NewsRepository;
import com.end2end.ansimnuri.news.dto.NewsDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class NewsServiceImpl implements NewsService {
    private final NewsRepository newsRepository;

    @Value("${naver.news.api.key}")
    private String naverNewApiKey;

    @Value("${naver.news.id.key}")
    private String naverNewsIdKey;

    @Scheduled(cron = "0 22 15 * * *")
    @Transactional
    @Override
    public void insert() {
        RestTemplate restTemplate = new RestTemplate();
        try {
            String apiUrl = "https://openapi.naver.com/v1/search/news.json?query=서울 (살인 OR 폭행 OR 범죄 OR 강도)&display=20&sort=date";

            // 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-Naver-Client-Id", naverNewsIdKey);
            headers.set("X-Naver-Client-Secret", naverNewApiKey);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);
            System.out.println(response.getBody());
            JsonNode root = new ObjectMapper().readTree(response.getBody());
            JsonNode items = root.path("items"); // 네이버 뉴스 API는 "items" 배열 반환

            List<News> newsList = new ArrayList<>();
            for (JsonNode item : items) {
                String checkUrl = item.path("link").asText();
                if (newsRepository.existsByUrl(checkUrl)) {
                    continue;
                }

                String pubDateStr = item.path("pubDate").asText(); // e.g., "Wed, 15 May 2024 09:00:00 +0900"
                DateTimeFormatter formatter = DateTimeFormatter.RFC_1123_DATE_TIME;
                ZonedDateTime zdt = ZonedDateTime.parse(pubDateStr, formatter);
                LocalDateTime regDate = zdt.toLocalDateTime();

                News news = News.builder()
                        .title(item.path("title").asText())
                        .content(item.path("description").asText())
                        .description(item.path("description").asText())
                        .url(checkUrl)
                        .regDate(regDate)
                        .build();
                newsList.add(news);
            }

            newsRepository.saveAll(newsList);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("news api와의 연동이 오류되었습니다.", e);
        }
    }

    @Override
    public List<NewsDTO> allNews(){
        List<News> newsList = newsRepository.findAll();
        List<NewsDTO> dtoList = new ArrayList<>();
        for(News news : newsList){
          NewsDTO newsDTO= new NewsDTO(
                  news.getContent(),
                  news.getDescription(),
                  news.getTitle(),
                  news.getUrl(),
                  news.getRegDate()
          );
          dtoList.add(newsDTO);
        }
        return dtoList;
    }
}
