package com.end2end.ansimnuri.news.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "뉴스 DTO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewsDTO {
    @Schema(description = "제목", example = "xx시 oo동 방화 사건 발생")
    private String title;
    @Schema(description = "내용", example = "오늘(xx일)에 xx시 oo동에서...")
    private String content;
    @Schema(description = "섬네일 이미지", example = "https://...")
    private String thumbnailImg;
    @Schema(description = "url", example = "https://...")
    private String url;
    @Schema(description = "등록 일자", example = "2024-04-30 09:00:00", format = "date-time")
    private String regDate;
}
