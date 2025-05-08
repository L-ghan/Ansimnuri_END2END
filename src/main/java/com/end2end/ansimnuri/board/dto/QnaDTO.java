package com.end2end.ansimnuri.board.dto;

import com.end2end.ansimnuri.board.domain.entity.Qna;
import com.end2end.ansimnuri.member.domain.entity.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Schema(description = "Q&A DTO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QnaDTO {
    @Schema(description = "Q&A id", example = "1", minimum = "1")
    private long id;
    @Schema(description = "글쓴이 id", example = "1", minimum = "1")
    private long userId;
    @Schema(description = "글쓴이 닉네임", example = "홍길동")
    private String nickname;
    @Schema(description = "제목", example = "지도 사용관련으로 문의가 있습니다.")
    private String title;
    @Schema(description = "내용", example = "지도에 네비게이션을...")
    private String content;
    @Schema(description = "등록일자", example = "2024-02-20 10:00:00", format = "date-time")
    private LocalDateTime regDate;

    public static QnaDTO of(Qna qna) {
        return QnaDTO.builder()
                .id(qna.getId())
                .userId(qna.getMember().getId())
                .nickname(qna.getMember().getNickname())
                .title(qna.getTitle())
                .content(qna.getContent())
                .regDate(qna.getRegDt())
                .build();
    }
}
