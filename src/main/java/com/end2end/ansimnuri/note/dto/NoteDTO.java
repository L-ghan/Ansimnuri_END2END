package com.end2end.ansimnuri.note.dto;

import com.end2end.ansimnuri.note.domain.entity.Note;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Schema(description = "쪽지 DTO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NoteDTO {
    @Schema(description = "쪽지 id", example = "1", minimum = "1")
    private long id;
    private long userId;
    private String nickname;
    private String content;
    private double latitude;
    private double longitude;
    private int recCount;
    private LocalDateTime regDate;

    public static NoteDTO of(Note note, int recCount) {
        return NoteDTO.builder()
                .id(note.getId())
                .userId(note.getMember().getId())
                .nickname(note.getMember().getNickname())
                .content(note.getContent())
                .latitude(note.getLatitude())
                .longitude(note.getLongitude())
                .recCount(recCount)
                .regDate(note.getRegDt())
                .build();
    }
}
