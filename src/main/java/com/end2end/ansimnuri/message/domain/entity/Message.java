package com.end2end.ansimnuri.message.domain.entity;

import com.end2end.ansimnuri.message.dto.MessageDTO;
import com.end2end.ansimnuri.user.domain.entity.Member;
import com.end2end.ansimnuri.util.entity.Timestamp;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@SequenceGenerator(
        name = "messageSequenceGenerator",
        sequenceName = "MESSAGE_ID_SEQ",
        allocationSize = 1
)
@Table(name = "MESSAGE")
@Entity
public class Message extends Timestamp {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "messageSequenceGenerator")
    private Long id;
    @Column(name = "CONTENT", nullable = false)
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MESSAGE_ROOM_ID", nullable = false)
    private MessageRoom messageRoom;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID", nullable = false)
    private Member member;

    public static Message of(MessageDTO messageDTO) {
        return Message.builder()
                .build();
    }

    public void update(MessageDTO messageDTO) {}
}
