package com.end2end.ansimnuri.map.domain.repository;

import com.end2end.ansimnuri.map.domain.entity.SearchHistory;
import com.end2end.ansimnuri.member.domain.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SearchHistoryRepository extends JpaRepository<SearchHistory, Long> {
    List<SearchHistory> findByMember(Member member);
}
