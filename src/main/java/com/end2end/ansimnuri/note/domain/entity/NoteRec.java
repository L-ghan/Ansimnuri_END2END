package com.end2end.ansimnuri.note.domain.entity;

import com.end2end.ansimnuri.user.domain.entity.Member;
import com.end2end.ansimnuri.util.entity.Timestamp;
import jakarta.persistence.*;
import lombok.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@SequenceGenerator(
        name="noteRecSequenceGenerator",
        sequenceName = "NOTE_REC_ID_SEQ",
        allocationSize = 1
)
@Table(name = "NOTE_REC")
@Entity
public class NoteRec extends Timestamp {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "noteRecSequenceGenerator")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NOTE_ID", nullable = false)
    private Note note;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID", nullable = false)
    private Member member;

    public void update() {}
}
