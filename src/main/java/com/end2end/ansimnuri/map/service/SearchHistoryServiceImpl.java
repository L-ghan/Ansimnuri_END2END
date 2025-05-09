package com.end2end.ansimnuri.map.service;

import com.end2end.ansimnuri.map.domain.entity.SearchHistory;
import com.end2end.ansimnuri.map.domain.repository.SearchHistoryRepository;
import com.end2end.ansimnuri.map.dto.SearchHistoryDTO;
import com.end2end.ansimnuri.member.domain.entity.Member;
import com.end2end.ansimnuri.member.domain.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class SearchHistoryServiceImpl implements SearchHistoryService {
    private final SearchHistoryRepository searchHistoryRepository;
    private final MemberRepository memberRepository;

    @Override
    public List<SearchHistoryDTO> selectByMemberId(long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 id의 유저가 없습니다."));

        return searchHistoryRepository.findByMember(member).stream()
                .map(SearchHistoryDTO::of)
                .toList();
    }

    @Transactional
    @Override
    public void insert(SearchHistoryDTO dto) {
        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("해당 id의 유저가 없습니다."));
        searchHistoryRepository.save(SearchHistory.of(dto.getSearchKeyword(), member));
    }

    @Override
    public void deleteById(long id) {
        SearchHistory searchHistory = searchHistoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 id의 검색 기록이 없습니다."));
        searchHistoryRepository.delete(searchHistory);
    }
}
