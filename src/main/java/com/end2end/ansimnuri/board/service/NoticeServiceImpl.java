package com.end2end.ansimnuri.board.service;

import com.end2end.ansimnuri.board.dao.NoticeDAO;
import com.end2end.ansimnuri.board.domain.entity.Notice;
import com.end2end.ansimnuri.board.domain.repository.NoticeRepository;
import com.end2end.ansimnuri.board.dto.NoticeDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.end2end.ansimnuri.util.Statics.RECORD_COUNT_PER_PAGE;

@RequiredArgsConstructor
@Service
public class NoticeServiceImpl implements NoticeService {
    private final NoticeDAO noticeDAO;
    private final NoticeRepository noticeRepository;

    @Override
    public List<NoticeDTO> selectAll(int page) {
        int start = (page - 1) * RECORD_COUNT_PER_PAGE;
        int end = Math.min(page * RECORD_COUNT_PER_PAGE, noticeDAO.countAll());

        return noticeDAO.selectAll(start, end);
    }

    @Override
    public List<NoticeDTO> selectByTitleLike(String searchKey, int page) {
        int start = (page - 1) * RECORD_COUNT_PER_PAGE;
        int end = Math.min(page * RECORD_COUNT_PER_PAGE, noticeDAO.countByTitleLike(searchKey));

        return noticeDAO.selectByTitleLike(searchKey, start, end);
    }

    @Override
    public NoticeDTO selectById(long id) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("%d와 일치하는 id를 가진 공지사항이 존재하지 않습니다.", id)));

        return NoticeDTO.of(notice);
    }

    @Override
    public void insert(NoticeDTO noticeDTO) {
        noticeRepository.save(Notice.of(noticeDTO));
    }

    @Override
    public void update(NoticeDTO noticeDTO) {
        Notice notice = noticeRepository.findById(noticeDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("%d와 일치하는 id를 가진 공지사항이 존재하지 않습니다.", noticeDTO.getId())));

        notice.update(noticeDTO);
    }

    @Override
    public void deleteById(long id) {
        Notice notice = noticeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("%d와 일치하는 id를 가진 공지사항이 존재하지 않습니다.", id)));

        noticeRepository.delete(notice);
    }
}
