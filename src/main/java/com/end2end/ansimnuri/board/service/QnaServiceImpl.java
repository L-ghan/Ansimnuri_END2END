package com.end2end.ansimnuri;

import com.end2end.ansimnuri.board.dao.QnaDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class QnaServiceImpl implements QnaService {
    private final QnaDAO qnaDAO;
}
