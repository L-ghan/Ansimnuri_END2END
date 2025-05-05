package com.end2end.ansimnuri.board.service;

import com.end2end.ansimnuri.board.dto.NoticeDTO;

import java.util.List;

public interface NoticeService {
    List<NoticeDTO> selectAll();
}
