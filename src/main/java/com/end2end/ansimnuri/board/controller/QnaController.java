package com.end2end.ansimnuri;

import com.end2end.ansimnuri.board.service.QnaReplyService;
import com.end2end.ansimnuri.board.service.QnaService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/qna")
@RestController
public class QnaController {
    private final QnaService qnaService;
    private final QnaReplyService qnaReplyService;
}
