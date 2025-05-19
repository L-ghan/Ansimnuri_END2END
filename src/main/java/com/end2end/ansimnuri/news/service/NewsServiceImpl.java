package com.end2end.ansimnuri.news.service;
import io.github.bonigarcia.wdm.WebDriverManager;
import com.end2end.ansimnuri.news.domain.entity.News;
import com.end2end.ansimnuri.news.domain.repository.NewsRepository;
import com.end2end.ansimnuri.news.dto.NewsDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class NewsServiceImpl implements NewsService {
    private final NewsRepository newsRepository;

    @Value("${naver.news.api.key}")
    private String naverNewApiKey;

    @Value("${naver.news.id.key}")
    private String naverNewsIdKey;

    @Value("${openai.api.key}")
    private String openaiApiKey;

    @Scheduled(cron = "0 32 15 * * *")
    @Transactional
    @Override
    public void insert() {
        RestTemplate restTemplate = new RestTemplate();
        try {
            String apiUrl = "https://openapi.naver.com/v1/search/news.json?query=서울 AND 살인&display=10&sort=date";

            // 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.set("X-Naver-Client-Id", naverNewsIdKey);
            headers.set("X-Naver-Client-Secret", naverNewApiKey);

            HttpEntity<String> entity = new HttpEntity<>(headers);

            ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);
            JsonNode root = new ObjectMapper().readTree(response.getBody());
            JsonNode items = root.path("items");

            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");
            options.addArguments("--disable-gpu");
            WebDriver driver = new ChromeDriver(options);

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

                String thumbnailUrl = "";
                try {
                    driver.get(checkUrl);
                    WebElement metaOgImage = driver.findElement(By.cssSelector("meta[property='og:image']"));
                    thumbnailUrl = metaOgImage.getAttribute("content");
                } catch (Exception e) {
                    System.out.println("썸네일 추출 실패: " + checkUrl);
                }

                String title = item.path("title").asText();
                System.out.println(title);
                if (!isMurderRelated(title)) {
                    System.out.println(isMurderRelated(title));
                    continue;
                }

                News news = News.builder()
                        .title(item.path("title").asText())
                        .description(item.path("description").asText())
                        .url(checkUrl)
                        .regDate(regDate)
                        .img(thumbnailUrl)
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
                  news.getDescription(),
                  news.getTitle(),
                  news.getUrl(),
                  news.getRegDate(),
                  news.getImg()
          );
          dtoList.add(newsDTO);
        }
        return dtoList;
    }
    private boolean isMurderRelated(String title) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(openaiApiKey); // OpenAI API 키

        List<Map<String, String>> messages = List.of(
                Map.of("role", "user", "content", title + " 이 뉴스 제목이 살인 범죄와 실제로 관련이 있다면 true, 아니면 false만 대답해줘."
                )
        );

        Map<String, Object> body = Map.of(
                "model", "gpt-3.5-turbo",
                "messages", messages
        );

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(
                    "https://api.openai.com/v1/chat/completions",
                    entity,
                    String.class
            );

            JsonNode root = new ObjectMapper().readTree(response.getBody());
            String content = root
                    .path("choices").get(0)
                    .path("message")
                    .path("content")
                    .asText()
                    .trim()
                    .toLowerCase();

            return content.contains("true");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
