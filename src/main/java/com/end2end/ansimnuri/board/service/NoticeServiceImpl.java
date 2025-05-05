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
        return noticeDAO.selectAll();
    }

    @Override
    public NoticeDTO selectById(long id) {
        return noticeDAO.selectById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("%d와 일치하는 id를 가진 공지사항이 존재하지 않습니다.", id)));
    }

    @Override
    public void insert(NoticeDTO noticeDTO) {

    }

    @Override
    public void update(NoticeDTO noticeDTO) {

    }

    @Override
    public void deleteById(long id) {

    }
}
