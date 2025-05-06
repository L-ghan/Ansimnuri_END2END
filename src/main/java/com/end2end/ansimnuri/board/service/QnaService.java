package com.end2end.ansimnuri.board.service;

import com.end2end.ansimnuri.board.dto.QnaDTO;

import java.util.List;

public interface QnaService {
    List<QnaDTO> selectAll(int page);
    List<QnaDTO> selectByUserId(long userId, int page);
    QnaDTO selectById(long id);
    void insert(QnaDTO qnaDTO);
    void update(QnaDTO qnaDTO);
    void deleteById(long id);
}
