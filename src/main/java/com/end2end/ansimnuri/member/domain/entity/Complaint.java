package com.end2end.ansimnuri.member.domain.entity;

import com.end2end.ansimnuri.util.entity.Timestamp;
import com.end2end.ansimnuri.util.enums.Yn;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@SequenceGenerator(
        name = "complaintSequenceGenerator",
        sequenceName = "COMPLAINT_ID_SEQ",
        allocationSize = 1
)
@Table(name = "COMPLAINT")
@Entity
public class Complaint extends Timestamp {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "complaintSequenceGenerator")
    private Long id;
    @Column(name = "REASON", nullable = false)
    private String reason;
    @Column(name = "SUBMIT_YN")
    @Enumerated(EnumType.STRING)
    private Yn submitYn;
    @Column(name = "SUBMIT_DATE")
    private LocalDateTime submitDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REPORTED_ID", nullable = false)
    private Member reporter;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REPORTEE_ID", nullable = false)
    private Member reportee;

    public void update() {}
}
