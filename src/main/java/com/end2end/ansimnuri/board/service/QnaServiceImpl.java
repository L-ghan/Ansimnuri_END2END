package com.end2end.ansimnuri.board.service;

import com.end2end.ansimnuri.board.dao.QnaDAO;
import com.end2end.ansimnuri.board.dto.QnaDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class QnaServiceImpl implements QnaService {
    private final QnaDAO qnaDAO;

    @Override
    public List<QnaDTO> selectAll(int page) {
        return List.of();
    }

    @Override
    public List<QnaDTO> selectByUserId(long userId, int page) {
        return List.of();
    }

    @Override
    public QnaDTO selectById(long id) {
        return null;
    }

    @Override
    public void insert(QnaDTO qnaDTO) {

    }

    @Override
    public void update(QnaDTO qnaDTO) {

    }

    @Override
    public void deleteById(long id) {

    }
}
