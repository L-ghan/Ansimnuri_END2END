package com.end2end.ansimnuri.note.dto;

import com.end2end.ansimnuri.note.domain.entity.NoteRec;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoteRecDTO {
    private long id;
    private long userId;
    private long noteId;
    private LocalDateTime regDate;

    public static NoteRecDTO of(NoteRec noteRec) {
        return NoteRecDTO.builder()
                .id(noteRec.getId())
                .userId(noteRec.getMember().getId())
                .noteId(noteRec.getNote().getId())
                .regDate(noteRec.getRegDt())
                .build();
    }
}
