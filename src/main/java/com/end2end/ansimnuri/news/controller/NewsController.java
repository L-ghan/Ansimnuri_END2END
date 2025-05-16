package com.end2end.ansimnuri.news.controller;

import com.end2end.ansimnuri.news.dto.NewsDTO;
import com.end2end.ansimnuri.news.service.NewsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "뉴스 조회 api", description = "news api에 요청 후 가져온 뉴스 데이터를 조회한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 작동입니다."),
            @ApiResponse(responseCode = "500", description = "news api 통신 오류")
    })
    @GetMapping
    public List<NewsDTO> getNews() {
        return newsService.allNews();
    }

    @GetMapping("/test")
    public void test() {
        newsService.insert();
    }
}
