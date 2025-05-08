package com.end2end.ansimnuri.message.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@SequenceGenerator(
        name = "messageRoomSequenceGenerator",
        sequenceName = "MESSAGE_ROOM_ID_SEQ",
        allocationSize = 1
)
@Table(name = "MESSAGE_ROOM")
@Entity
public class MessageRoom {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "messageRoomSequenceGenerator")
    private Long id;
    @Column(name = "NAME", nullable = false)
    private String name;

    @OneToMany(mappedBy = "messageRoom")
    private List<Message> messageList;
}
