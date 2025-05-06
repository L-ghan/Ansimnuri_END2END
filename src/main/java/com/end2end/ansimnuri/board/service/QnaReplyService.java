package com.end2end.ansimnuri.board.service;

import com.end2end.ansimnuri.board.dto.QnaReplyDTO;

public interface QnaReplyService {
    QnaReplyDTO selectByQnaId(long qnaId);
    void insert(QnaReplyDTO qnaReplyDTO);
    void update(QnaReplyDTO qnaReplyDTO);
    void deleteById(long id);
}
