package com.end2end.ansimnuri.board.domain.entity;

import com.end2end.ansimnuri.board.dto.NoticeDTO;
import com.end2end.ansimnuri.util.entity.Timestamp;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@SequenceGenerator(
        name = "noticeSequenceGenerator",
        sequenceName = "NOTICE_ID_SEQ",
        allocationSize = 1)
@Table(name = "NOTICE")
@Entity
public class Notice extends Timestamp {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "noticeSequenceGenerator")
    private Long id;
    @Column(name="TITLE", nullable = false)
    private String title;
    @Column(name="CONTENT", nullable = false)
    private String content;

    public void update (NoticeDTO noticeDTO) {
        this.title = noticeDTO.getTitle();
        this.content = noticeDTO.getContent();
    }

    public static Notice of (NoticeDTO noticeDTO) {
        return Notice.builder()
                .title(noticeDTO.getTitle())
                .content(noticeDTO.getContent())
                .build();
    }
}
