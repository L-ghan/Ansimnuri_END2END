package com.end2end.ansimnuri.board.controller;

import com.end2end.ansimnuri.board.service.QnaReplyService;
import com.end2end.ansimnuri.board.service.QnaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Q&A API", description = "고객 질문과 답변을 수행하는 API")
@RequiredArgsConstructor
@RequestMapping("/qna")
@RestController
public class QnaController {
    private final QnaService qnaService;
    private final QnaReplyService qnaReplyService;
}
