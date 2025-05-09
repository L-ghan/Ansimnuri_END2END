package com.end2end.ansimnuri.news.controller;

import com.end2end.ansimnuri.news.dto.NewsDTO;
import com.end2end.ansimnuri.news.service.NewsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "news API", description = "뉴스 데이터 기능을 가진 API")
@RequiredArgsConstructor
@RequestMapping("/api/news")
@RestController
public class NewsController {
    private final NewsService newsService;

    @GetMapping
    public List<NewsDTO> getNews() {
        return newsService.fetchNews();
    }
}
