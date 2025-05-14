package com.end2end.ansimnuri.map.dto;

import com.end2end.ansimnuri.map.domain.entity.SearchHistory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Schema(description = "검색 기록 DTO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchHistoryDTO {
    @Schema(description = "id", example = "1", minimum = "1")
    private long id;
    @Schema(description = "유저 id", example = "1", minimum = "1")
    private long memberId;
    @Schema(description = "검색 내용", example = "xx시")
    private String searchKeyword;
    @Schema(description = "등록 일자", example = "2024-10-10 09:00:00", format = "date-time")
    private LocalDateTime regDate;

    public static SearchHistoryDTO of(SearchHistory searchHistory) {
        return SearchHistoryDTO.builder()
                .id(searchHistory.getId())
                .memberId(searchHistory.getMember().getId())
                .searchKeyword(searchHistory.getSearchKeyword())
                .regDate(searchHistory.getRegDt())
                .build();
    }
}
