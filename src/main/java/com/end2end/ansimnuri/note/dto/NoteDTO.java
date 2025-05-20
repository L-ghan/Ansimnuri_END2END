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
    @Schema(description = "글쓴이 id", example = "1", minimum = "1")
    private long userId;
    @Schema(description = "글쓴이 닉네임", example = "홍길동")
    private String nickname;
    @Schema(description = "내용", example = "쪽지 내용입니다.")
    private String content;
    @Schema(description = "위도", example = "126.977963")
    private double latitude;
    @Schema(description = "경도", example = "37.56647")
    private double longitude;
    @Schema(description = "추천 갯수", example = "10", minimum = "0")
    private int recCount;
    @Schema(description = "댓글 갯수", example = "1", minimum = "0")
    private int replyCount;
    @Schema(description = "등록 일자",example = "2024-02-20 10:00:00", format = "date-time")
    private LocalDateTime regDate;

    public static NoteDTO of(Note note) {
        return NoteDTO.builder()
                .id(note.getId())
                .userId(note.getMember().getId())
                .nickname(note.getMember().getNickname())
                .content(note.getContent())
                .latitude(note.getLatitude())
                .longitude(note.getLongitude())
                .recCount(note.getNoteRecList().size())
                .replyCount(note.getNoteReplyList().size())
                .regDate(note.getRegDt())
                .build();
    }
}
