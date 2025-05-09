package com.end2end.ansimnuri.news.service;

import com.end2end.ansimnuri.news.dto.NewsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class NewsServiceImpl implements NewsService {
    @Value("${news.api.key}")
    private String apiKey;

    @Override
    public List<NewsDTO> fetchNews() {
        String url = "https://newsapi.org/v2/everything?"
                + "q=범죄&살인&상해&폭행&묻지마&강도&절도"
                + "&apiKey=" + apiKey;

    }
}
