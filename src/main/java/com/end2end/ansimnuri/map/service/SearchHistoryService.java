package com.end2end.ansimnuri.map.service;

import com.end2end.ansimnuri.map.dto.SearchHistoryDTO;

import java.util.List;

public interface SearchHistoryService {
    List<SearchHistoryDTO> selectByMemberId(long memberId);
    void insert(SearchHistoryDTO dto);
    void deleteById(long id);
}
