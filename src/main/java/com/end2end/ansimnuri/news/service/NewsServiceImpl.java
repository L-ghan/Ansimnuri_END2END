package com.end2end.ansimnuri.news.service;

import com.end2end.ansimnuri.news.domain.entity.News;
import com.end2end.ansimnuri.news.domain.repository.NewsRepository;
import com.end2end.ansimnuri.news.dto.NewsDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class NewsServiceImpl implements NewsService {
    @Value("${news.api.key}")
    private String apiKey;
    private final RestTemplate restTemplate = new RestTemplate();
    private final NewsRepository newsRepository;
    @Override
    public List<NewsDTO> fetchNews() {
        List<NewsDTO> result = new ArrayList<>();
        try {
            String url = "https://newsapi.org/v2/everything?q=서울+(살인 OR 폭행 OR 범죄 OR 강도)&sortBy=publishedAt&apiKey=" + apiKey;
            String response = restTemplate.getForObject(url, String.class);

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

    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    @Override
    public void insert() {
        List<NewsDTO> result = new ArrayList<>();
        try {
            String url = "https://newsapi.org/v2/everything?q=서울+(살인 OR 폭행 OR 범죄 OR 강도)&sortBy=publishedAt&apiKey=" + apiKey;

            String response = restTemplate.getForObject(url, String.class);

            JsonNode root = new ObjectMapper().readTree(response);
            JsonNode articles = root.path("articles");
            System.out.println(articles.size());
            List<News> newsList = new ArrayList<>();
            for (JsonNode article : articles) {
                String checkUrl = article.path("url").asText();
                if (newsRepository.existsByUrl(checkUrl)) {
                    continue;
                }

                String publishedAt = article.path("publishedAt").asText();
                OffsetDateTime offsetDateTime = OffsetDateTime.parse(publishedAt); // Z는 ISO 형식이라 자동 인식
                LocalDateTime regDate = offsetDateTime.toLocalDateTime(); // 시간대 제거

                System.out.println(article);
                News news = News.builder()
                        .title(article.path("title").asText())
                        .content(article.path("content").asText())
                        .thumbnailImg(article.path("urlToImage").asText())
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
    public List<News> allNews(){
        List<News> newsList = newsRepository.findAll();
        System.out.println(newsList);
        return newsList;
    }
}
