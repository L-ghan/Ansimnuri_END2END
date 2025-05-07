package com.end2end.ansimnuri.board.service;

import com.end2end.ansimnuri.board.dao.QnaDAO;
import com.end2end.ansimnuri.board.dto.QnaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.end2end.ansimnuri.util.Statics.RECORD_COUNT_PER_PAGE;

@RequiredArgsConstructor
@Service
public class QnaServiceImpl implements QnaService {
    private final QnaDAO qnaDAO;

    @Override
    public List<QnaDTO> selectAll(int page) {
        int start = (page - 1) * 10;
        int end = Math.min(page * RECORD_COUNT_PER_PAGE, qnaDAO.countAll());

        return qnaDAO.selectAll(start, end);
    }

    @Override
    public List<QnaDTO> selectByUserId(long userId, int page) {
        int start = (page - 1) * 10;
        int end = Math.min(page * RECORD_COUNT_PER_PAGE, qnaDAO.countByUserId(userId));

        return qnaDAO.selectByUserId(userId, start, end);
    }

    @Override
    public QnaDTO selectById(long id) {
        return qnaDAO.selectById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("%d에 해당하는 ID가 존재하지 않습니다.", id)));
    }

    @Override
    public void insert(QnaDTO qnaDTO) {
        qnaDAO.insert(qnaDTO);
    }

    @Override
    public void update(QnaDTO qnaDTO) {
        qnaDAO.update(qnaDTO);
    }

    @Override
    public void deleteById(long id) {
        qnaDAO.deleteById(id);
    }
}
