package com.end2end.ansimnuri.note.dto;

import com.end2end.ansimnuri.note.domain.entity.Note;
import com.end2end.ansimnuri.util.enums.RequestType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoteSocketDTO {
    private RequestType requestType;
    private long requesterId;
    private NoteDTO noteDTO;

    public static NoteSocketDTO of(RequestType requestType, long requesterId, NoteDTO note) {
        return NoteSocketDTO.builder()
                .requestType(requestType)
                .requesterId(requesterId)
                .noteDTO(note)
                .build();
    }
}
