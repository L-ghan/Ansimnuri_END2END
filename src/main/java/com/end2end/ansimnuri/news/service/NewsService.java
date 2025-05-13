package com.end2end.ansimnuri.news.service;

import com.end2end.ansimnuri.news.domain.entity.News;
import com.end2end.ansimnuri.news.dto.NewsDTO;

import java.util.List;

public interface NewsService {
    void insert();

    List<NewsDTO> allNews();
}
