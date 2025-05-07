package com.end2end.ansimnuri.board.service;

import com.end2end.ansimnuri.board.dto.NoticeDTO;

import java.util.List;

public interface NoticeService {
    List<NoticeDTO> selectAll(int page);
    List<NoticeDTO> selectByTitleLike(String searchKey, int page);
    NoticeDTO selectById(long id);
    void insert(NoticeDTO noticeDTO);
    void update(NoticeDTO noticeDTO);
    void deleteById(long id);
}
