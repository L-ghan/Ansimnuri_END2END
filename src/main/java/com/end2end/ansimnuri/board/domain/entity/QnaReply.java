package com.end2end.ansimnuri.board.domain.entity;

import com.end2end.ansimnuri.util.entitly.Timestamp;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@SequenceGenerator(
        name = "qnaReplySequenceGenerator",
        sequenceName = "QNA_REPLY_ID_SEQ",
        allocationSize = 1
)
@Table(name = "QNA_REPLY")
@Entity
public class QnaReply extends Timestamp {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "qnaReplySequenceGenerator")
    private Long id;
    @Column(name="CONTENT", nullable = false)
    private String content;
    @OneToOne
    @JoinColumn(name = "QNA_ID", nullable = false)
    private Qna qna;

    public static QnaReply of (String content) {
        return QnaReply.builder()
                .content(content)
                .build();
    }

    public void update (String content) {
        this.content = content;
    }
}
