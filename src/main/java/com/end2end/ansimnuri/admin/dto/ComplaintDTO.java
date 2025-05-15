package com.end2end.ansimnuri.admin.dto;

import com.end2end.ansimnuri.admin.domain.entity.Complaint;
import com.end2end.ansimnuri.util.enums.Yn;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ComplaintDTO {
    private long id;
    private long reporterId;
    private long reporteeId;
    private Yn submitYn;
    private LocalDateTime submitDate;
    private String reason;
    private LocalDateTime regDate;

    public static ComplaintDTO of(Complaint complaint) {
        return ComplaintDTO.builder()
                .id(complaint.getId())
                .reporterId(complaint.getReporter().getId())
                .reporteeId(complaint.getReportee().getId())
                .submitYn(complaint.getSubmitYn())
                .submitDate(complaint.getSubmitDate())
                .reason(complaint.getReason())
                .regDate(complaint.getRegDt())
                .build();
    }
}
