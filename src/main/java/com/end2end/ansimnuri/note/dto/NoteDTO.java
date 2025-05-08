package com.end2end.ansimnuri.note.dto;

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
    private String content;
    private double latitude;
    private double longitude;
    private LocalDateTime regDate;
}
