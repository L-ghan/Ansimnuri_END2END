package com.end2end.ansimnuri.board.service;

import com.end2end.ansimnuri.board.dao.NoticeDAO;
import com.end2end.ansimnuri.board.dto.NoticeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class NoticeServiceImpl implements NoticeService {
    private final NoticeDAO noticeDAO;

    @Override
    public List<NoticeDTO> selectAll() {
        return List.of();
    }
}
