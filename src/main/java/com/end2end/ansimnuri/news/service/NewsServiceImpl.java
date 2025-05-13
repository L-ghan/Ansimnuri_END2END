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

    @Scheduled(cron = "0 0 9 * * *")
    @Transactional
    @Override
    public void insert() {
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
                OffsetDateTime offsetDateTime = OffsetDateTime.parse(publishedAt);
                LocalDateTime regDate = offsetDateTime.toLocalDateTime();

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
    public List<NewsDTO> allNews(){
        List<News> newsList = newsRepository.findAll();
        List<NewsDTO> dtoList = new ArrayList<>();
        for(News news : newsList){
          NewsDTO newsDTO= new NewsDTO(
                  news.getContent(),
                  news.getThumbnailImg(),
                  news.getTitle(),
                  news.getUrl(),
                  news.getRegDate()
          );
          dtoList.add(newsDTO);
        }
        return dtoList;
    }
}
