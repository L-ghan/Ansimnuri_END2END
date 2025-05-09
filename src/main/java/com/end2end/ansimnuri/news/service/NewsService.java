package com.end2end.ansimnuri.news.service;

import com.end2end.ansimnuri.news.dto.NewsDTO;

import java.util.List;

public interface NewsService {
    List<NewsDTO> fetchNews();
    void insert();
}
