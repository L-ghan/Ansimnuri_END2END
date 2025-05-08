package com.end2end.ansimnuri.board.dto;

import com.end2end.ansimnuri.board.domain.entity.QnaReply;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Schema(description = "Q&A 댓글 DTO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QnaReplyDTO {
    private long id;
    private long qnaId;
    private String content;
    private LocalDateTime regDate;

    public static QnaReplyDTO of (QnaReply qnaReply) {
        return QnaReplyDTO.builder()
                .id(qnaReply.getId())
                .qnaId(qnaReply.getQna().getId())
                .content(qnaReply.getContent())
                .regDate(qnaReply.getRegDt())
                .build();
    }
}
