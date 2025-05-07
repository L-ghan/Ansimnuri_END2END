package com.end2end.ansimnuri.board.domain.entity;

import com.end2end.ansimnuri.user.domain.entity.Member;
import com.end2end.ansimnuri.util.entitly.Timestamp;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@SequenceGenerator(
        name = "qnaSequenceGenerator",
        sequenceName = "QNA_ID_SEQ",
        allocationSize = 1
)
@Table(name = "QNA")
@Entity
public class Qna extends Timestamp {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "qnaSequenceGenerator")
    private Long id;
    @Column(name="TITLE", nullable = false)
    private String title;
    @Column(name="CONTENT", nullable = false)
    private String content;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID", nullable = false)
    public Member member;

    @OneToOne(mappedBy = "qna")
    private QnaReply qnaReply;

    public static Qna of (String title, String content) {
        return Qna.builder()
                .title(title)
                .content(content)
                .build();
    }

    public void update (String title, String content) {
        this.title = title;
        this.content = content;
    }
}
