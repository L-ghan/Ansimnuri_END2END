package com.end2end.ansimnuri.board.controller;

import com.end2end.ansimnuri.board.dto.QnaDTO;
import com.end2end.ansimnuri.board.dto.QnaReplyDTO;
import com.end2end.ansimnuri.board.service.QnaReplyService;
import com.end2end.ansimnuri.board.service.QnaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "모든 Q&A 조회 api", description = "해당 페이지의 Q&A 데이터를 조회한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 작동입니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 페이지 번호입니다.")
    })
    @GetMapping
    public ResponseEntity<List<QnaDTO>> selectAll(
            @Parameter(description = "페이지")
            @RequestParam(defaultValue = "1") int page) {
        return ResponseEntity.ok(qnaService.selectAll(page));
    }

    @Operation(summary = "유저의 모든 Q&A 조회 api",
            description = "해당 페이지의 해당 유저 ID에 해당하는 Q&A 데이터를 조회한다.")
    @Parameters({
            @Parameter(name = "userId", description = "유저 id"),
            @Parameter(name="page", description = "페이지")
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 작동입니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 페이지 번호입니다."),
            @ApiResponse(responseCode = "401", description = "해당 서비스는 로그인이 필요한 서비스입니다.")
    })
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<QnaDTO>> selectByUserId(
            @PathVariable long userId, @RequestParam(defaultValue = "1") int page) {
        return ResponseEntity.ok(qnaService.selectByUserId(userId, page));
    }

    @Operation(summary = "해당 ID의 Q&A 조회 api", description = "해당 ID를 가진 Q&A 데이터를 조회한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 작동입니다."),
            @ApiResponse(responseCode = "404", description = "해당 ID의 Q&A가 존재하지 않습니다.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<QnaDTO> selectById(@PathVariable long id) {
        return ResponseEntity.ok(qnaService.selectById(id));
    }

    @Operation(summary = "Q&A 등록 api", description = "Q&A를 등록한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 작동입니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 입력 데이터입니다."),
            @ApiResponse(responseCode = "401", description = "해당 서비스는 로그인이 필요한 서비스입니다.")
    })
    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody QnaDTO qnaDTO) {
        qnaService.insert(qnaDTO);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Q&A 수정 api", description = "Q&A를 수정한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 작동입니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 입력 데이터입니다."),
            @ApiResponse(responseCode = "401", description = "해당 서비스는 로그인이 필요한 서비스입니다.")
    })
    @PutMapping
    public ResponseEntity<Void> update(@RequestBody QnaDTO qnaDTO) {
        qnaService.update(qnaDTO);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Q&A 삭제 api", description = "해당 ID를 가진 Q&A를 삭제한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 작동입니다."),
            @ApiResponse(responseCode = "400", description = "해당 ID를 가진 Q&A가 없습니다."),
            @ApiResponse(responseCode = "403", description = "관리자 및 글쓴 본인만 사용할 수 있는 요청입니다.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Q&A id")
            @PathVariable long id) {
        qnaService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "해당 Q&A ID의 Q&A 답글을 조회하는 api",
            description = "해당 Q&A ID에 일치하는 답글을 조회한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 작동입니다."),
            @ApiResponse(responseCode = "404", description = "해당 ID를 가진 Q&A가 존재하지 않습니다.")
    })
    @GetMapping("/reply/{qnaId}")
    public ResponseEntity<QnaReplyDTO> selectByQnaId(
            @Parameter(description = "Q&A id")
            @PathVariable long qnaId) {
        return ResponseEntity.ok(qnaReplyService.selectByQnaId(qnaId));
    }

    @Operation(summary = "Q&A 답글 등록 api", description = "Q&A 답글을 등록한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 작동입니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 입력 데이터입니다."),
            @ApiResponse(responseCode = "403", description = "해당 서비스는 관리자만 요청할 수 있습니다.")
    })
    @PostMapping("/reply")
    public ResponseEntity<Void> insert(@RequestBody QnaReplyDTO qnaReplyDTO) {
        qnaReplyService.insert(qnaReplyDTO);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Q&A 답글 수정 api", description = "Q&A 답글을 수정한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 작동입니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 입력 데이터입니다."),
            @ApiResponse(responseCode = "403", description = "해당 서비스는 관리자만 요청할 수 있습니다.")
    })
    @PutMapping("/reply")
    public ResponseEntity<Void> update(@RequestBody QnaReplyDTO qnaReplyDTO) {
        qnaReplyService.update(qnaReplyDTO);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Q&A 답글 삭제 api", description = "해당 ID를 가진 Q&A 답글을 삭제한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 작동입니다."),
            @ApiResponse(responseCode = "400", description = "해당 ID를 가진 Q&A 답글이 없습니다."),
            @ApiResponse(responseCode = "403", description = "해당 서비스는 관리자만 요청할 수 있습니다.")
    })
    @DeleteMapping("/reply/{id}")
    public ResponseEntity<Void> deleteById(
            @Parameter(description = "Q&A 답글 ID")
            @PathVariable long id) {
        qnaReplyService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
