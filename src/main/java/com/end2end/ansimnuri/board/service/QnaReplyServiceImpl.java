package com.end2end.ansimnuri.board.service;

import com.end2end.ansimnuri.board.dao.QnaReplyDAO;
import com.end2end.ansimnuri.board.dto.QnaReplyDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class QnaReplyServiceImpl implements QnaReplyService {
    private final QnaReplyDAO qnaReplyDAO;

    @Override
    public QnaReplyDTO selectByQnaId(long qnaId) {
        return qnaReplyDAO.selectByQnaId(qnaId);
    }

    @Override
    public void insert(QnaReplyDTO qnaReplyDTO) {
        qnaReplyDAO.insert(qnaReplyDTO);
    }

    @Override
    public void update(QnaReplyDTO qnaReplyDTO) {
        qnaReplyDAO.update(qnaReplyDTO);
    }

    @Override
    public void deleteById(long id) {
        qnaReplyDAO.deleteById(id);
    }
}
