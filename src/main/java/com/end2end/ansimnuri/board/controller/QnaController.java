package com.end2end.ansimnuri.board.controller;

import com.end2end.ansimnuri.board.dto.QnaDTO;
import com.end2end.ansimnuri.board.dto.QnaReplyDTO;
import com.end2end.ansimnuri.board.service.QnaReplyService;
import com.end2end.ansimnuri.board.service.QnaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Q&A API", description = "고객 질문과 답변을 수행하는 API")
@RequiredArgsConstructor
@RequestMapping("/qna")
@RestController
public class QnaController {
    private final QnaService qnaService;
    private final QnaReplyService qnaReplyService;

    @GetMapping
    public ResponseEntity<List<QnaDTO>> selectAll(@RequestParam(defaultValue = "1") int page) {
        return null;
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<QnaDTO>> selectByUserId(
            @PathVariable long userId, @RequestParam(defaultValue = "1") int page) {
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<QnaDTO> selectById(@PathVariable long id) {
        return null;
    }

    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody QnaDTO qnaDTO) {
        return ResponseEntity.ok().build();
    }

    @PutMapping
    public ResponseEntity<Void> update(@RequestBody QnaDTO qnaDTO) {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/reply/{qnaId}")
    public ResponseEntity<QnaReplyDTO> selectByQnaId(
            @PathVariable long qnaId) {
        return null;
    }

    @PostMapping("/reply")
    public ResponseEntity<Void> insert(@RequestBody QnaReplyDTO qnaReplyDTO) {
        return ResponseEntity.ok().build();
    }

    @PutMapping("/reply")
    public ResponseEntity<Void> update(@RequestBody QnaReplyDTO qnaReplyDTO) {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/reply/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable long id) {
        return ResponseEntity.ok().build();
    }
}
