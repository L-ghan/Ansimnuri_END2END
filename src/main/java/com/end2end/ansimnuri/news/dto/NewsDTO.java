package com.end2end.ansimnuri.news.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Schema(description = "뉴스 DTO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewsDTO {
    @Schema(description = "제목", example = "xx시 oo동 방화 사건 발생")
    private String title;
    @Schema(description = "요약", example = "https://...")
    private String description;
    @Schema(description = "url", example = "https://...")
    private String url;
    @Schema(description = "등록 일자", example = "2024-04-30 09:00:00", format = "date-time")
    private LocalDateTime regDate;
    private String img ;
}
