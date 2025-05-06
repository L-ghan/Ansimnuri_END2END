package com.end2end.ansimnuri.board.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Schema(description = "Q&A 댓글 DTO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QnaReplyDTO {
    private long id;
    private long qnaId;
    private String content;
    private Timestamp regDate;
}
