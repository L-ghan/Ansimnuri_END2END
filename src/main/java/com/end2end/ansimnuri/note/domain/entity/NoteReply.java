package com.end2end.ansimnuri.note.domain.entity;

import com.end2end.ansimnuri.user.domain.entity.Member;
import com.end2end.ansimnuri.util.entitly.Timestamp;
import jakarta.persistence.*;
import lombok.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@SequenceGenerator(
        name = "noteReplySequenceGenerator",
        sequenceName = "NOTE_REPLY_ID_SEQ",
        allocationSize = 1
)
@Table(name = "NOTE_REPLY")
@Entity
public class NoteReply extends Timestamp {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "noteReplySequenceGenerator")
    private Long id;
    @Column(name="CONTENT", nullable = false)
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NOTE_ID", nullable = false)
    private Note note;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID", nullable = false)
    private Member member;
}
