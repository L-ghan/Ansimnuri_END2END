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
        return null;
    }

    @Override
    public void insert(QnaReplyDTO qnaReplyDTO) {

    }

    @Override
    public void update(QnaReplyDTO qnaReplyDTO) {

    }

    @Override
    public void deleteById(long id) {

    }
}
