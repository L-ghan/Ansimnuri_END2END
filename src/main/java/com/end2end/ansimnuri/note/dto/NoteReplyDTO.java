package com.end2end.ansimnuri.note.dto;

import com.end2end.ansimnuri.note.domain.entity.NoteReply;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoteReplyDTO {
    private long id;
    private long noteId;
    private String content;
    private long userId;
    private String nickname;
    private LocalDateTime regDate;

    public static NoteReplyDTO of(NoteReply noteReply) {
        return NoteReplyDTO.builder()
                .id(noteReply.getId())
                .noteId(noteReply.getNote().getId())
                .content(noteReply.getContent())
                .userId(noteReply.getMember().getId())
                .nickname(noteReply.getMember().getNickname())
                .regDate(noteReply.getRegDt())
                .build();
    }
}
