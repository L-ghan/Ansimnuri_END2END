package com.end2end.ansimnuri.board.service;

import com.end2end.ansimnuri.board.dao.NoticeDAO;
import com.end2end.ansimnuri.board.dto.NoticeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.end2end.ansimnuri.util.Statics.RECORD_COUNT_PER_PAGE;

@RequiredArgsConstructor
@Service
public class NoticeServiceImpl implements NoticeService {
    private final NoticeDAO noticeDAO;

    @Override
    public List<NoticeDTO> selectAll(int page) {
        int start = (page - 1) * 10;
        int end = Math.min(page * RECORD_COUNT_PER_PAGE, noticeDAO.countAll());

        return noticeDAO.selectAll(start, end);
    }

    @Override
    public NoticeDTO selectById(long id) {
        return noticeDAO.selectById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("%d와 일치하는 id를 가진 공지사항이 존재하지 않습니다.", id)));
    }

    @Override
    public void insert(NoticeDTO noticeDTO) {
        noticeDAO.insert(noticeDTO);
    }

    @Override
    public void update(NoticeDTO noticeDTO) {
        noticeDAO.update(noticeDTO);
    }

    @Override
    public void deleteById(long id) {
        noticeDAO.deleteById(id);
    }
}
