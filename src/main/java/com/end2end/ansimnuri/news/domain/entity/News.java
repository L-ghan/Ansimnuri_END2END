package com.end2end.ansimnuri.news.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;


@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@SequenceGenerator(
        name = "newsSequenceGenerator",
        sequenceName = "NEWS_ID_SEQ",
        allocationSize = 1
)
@Table(name = "NEWS")
@Entity
public class News {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "newsSequenceGenerator")
    private Long id;
    @Column(name = "TITLE", nullable = false)
    private String title;
    @Column(name = "CONTENT", nullable = false)
    private String content;
    @Column(name = "THUMBNAIL_IMG", nullable = false)
    private String thumbnailImg;
    @Column(name = "URL", nullable = false)
    private String url;
    @Column (name = "REGDATE", nullable = false)
    private LocalDateTime regDate;
}
