package com.end2end.ansimnuri.news.domain.repository;

import com.end2end.ansimnuri.news.domain.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Long> {
    boolean existsByUrl(String checkUrl);
}
