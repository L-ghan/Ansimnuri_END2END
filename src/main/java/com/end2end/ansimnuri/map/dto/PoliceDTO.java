package com.end2end.ansimnuri.map.dto;

import com.end2end.ansimnuri.map.domain.entity.Police;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "경찰서 DTO")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PoliceDTO {
    @Schema(description = "id", example = "1", minimum = "1")
    private long id;
    @Schema(description = "이름", example = "서울중부")
    private String name;
    @Schema(description = "주소", example = "서울특별시 중구  을지로 234")
    private String address;
    @Schema(description = "위도", example = "126.927112885")
    private double latitude;
    @Schema(description = "경도", example = "37.490042567")
    private double longitude;
    @Schema(description = "종류", example = "지구대")
    private String type;

    public static PoliceDTO of(Police police) {
        return PoliceDTO.builder()
                .id(police.getId())
                .name(police.getName())
                .address(police.getAddress())
                .latitude(police.getLatitude())
                .longitude(police.getLongitude())
                .type(police.getType())
                .build();
    }
}
