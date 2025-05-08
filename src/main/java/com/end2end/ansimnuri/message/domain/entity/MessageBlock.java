package com.end2end.ansimnuri.message.domain.entity;

import com.end2end.ansimnuri.member.domain.entity.Member;
import com.end2end.ansimnuri.util.entity.Timestamp;
import jakarta.persistence.*;
import lombok.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@SequenceGenerator(
        name = "messageBlockSequenceGenerator",
        sequenceName = "MESSAGE_BLOCK_ID_SEQ",
        allocationSize = 1
)
@Table(name = "MESSAGE_BLOCK")
@Entity
public class MessageBlock extends Timestamp {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "messageBlockSequenceGenerator")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID", nullable = false)
    private Member member;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BLOCK_MEMBER_ID", nullable = false)
    private Member blockMember;
}
