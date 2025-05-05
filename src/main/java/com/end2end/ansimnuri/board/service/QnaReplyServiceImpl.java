package com.end2end.ansimnuri;

import com.end2end.ansimnuri.board.dao.QnaReplyDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class QnaReplyServiceImpl implements QnaReplyService {
    private final QnaReplyDAO qnaReplyDAO;
}
