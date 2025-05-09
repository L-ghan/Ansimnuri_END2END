package com.end2end.ansimnuri.map.domain.entity;

import com.end2end.ansimnuri.member.domain.entity.Member;
import com.end2end.ansimnuri.util.entity.Timestamp;
import jakarta.persistence.*;
import lombok.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Builder
@SequenceGenerator(
        name = "searchHistorySequenceGenerator",
        sequenceName = "SEARCH_HISTORY_ID_SEQ",
        allocationSize = 1
)
@Table(name = "SEARCH_HISTORY")
@Entity
public class SearchHistory extends Timestamp {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "searchHistorySequenceGenerator")
    private Long id;
    @Column(name = "SEARCH_KEYWORD", nullable = false)
    private String searchKeyword;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_ID", nullable = false)
    private Member member;

    public static SearchHistory of(String searchKeyword, Member member) {
        return SearchHistory.builder()
                .searchKeyword(searchKeyword)
                .member(member)
                .build();
    }
}
