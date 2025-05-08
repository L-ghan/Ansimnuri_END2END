package com.end2end.ansimnuri.board.dto;

import com.end2end.ansimnuri.board.domain.entity.Notice;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Schema(description = "공지사항 DTO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoticeDTO {
    @Schema(description = "공지사항 id", example = "1", minimum = "1")
    private long id;
    @Schema(description = "제목", example = "공지사항 제목입니다.")
    private String title;
    @Schema(description = "내용", example = "이번 공지사항은...")
    private String content;
    @Schema(description = "등록일자", example = "2024-02-20 10:00:00", format = "date-time")
    private LocalDateTime regDate;

    public static NoticeDTO of (Notice notice) {
        return NoticeDTO.builder()
                .id(notice.getId())
                .title(notice.getTitle())
                .content(notice.getContent())
                .regDate(notice.getRegDt())
                .build();
    }
}
