package com.end2end.ansimnuri.admin.domain.entity;


import com.end2end.ansimnuri.admin.dto.BlockDTO;
import com.end2end.ansimnuri.member.domain.entity.Member;
import com.end2end.ansimnuri.util.entity.Timestamp;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@SequenceGenerator(
        name="blockSequenceGenerator",
        sequenceName = "BLOCK_ID_SEQ",
        allocationSize = 1
)
@Table(name = "BLOCK")
@Entity
public class Block extends Timestamp {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "blockSequenceGenerator")
    private Long id;
    @Column(name = "REASON", nullable = false)
    private String reason;
    @Column(name = "END_DATE", nullable = false)
    private LocalDateTime endDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID", nullable = false)
    private Member member;

    public static Block of(BlockDTO dto, Member member) {
        return Block.builder()
                .reason(dto.getReason())
                .endDate(dto.getEndDate())
                .member(member)
                .build();
    }
}
