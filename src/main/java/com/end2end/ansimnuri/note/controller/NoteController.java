package com.end2end.ansimnuri.note.controller;

import com.end2end.ansimnuri.note.dto.NoteDTO;
import com.end2end.ansimnuri.note.dto.NoteRecDTO;
import com.end2end.ansimnuri.note.dto.NoteReplyDTO;
import com.end2end.ansimnuri.note.service.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name="쪽지 API", description = "쪽지 CRUD, 쪽지 추천, 쪽지 답글 CRUD 기능을 가진 API")
@RequiredArgsConstructor
@RequestMapping("/api/note")
@RestController
public class NoteController {
    private final NoteService noteService;
    private final NoteRecService noteRecService;
    private final NoteReplyService noteReplyService;

    @Operation(summary = "모든 쪽지 조회 API", description = "모든 쪽지를 조회한다.")
    @ApiResponse(responseCode = "200", description = "정상 작동입니다.")
    @GetMapping
    public ResponseEntity<List<NoteDTO>> selectAll() {
        return ResponseEntity.ok(noteService.selectAll());
    }

    @Operation(summary = "쪽지 등록 API", description = "쪽지를 등록한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 작동입니다."),
            @ApiResponse(responseCode = "400", description = "입력한 데이터가 잘못되었습니다."),
            @ApiResponse(responseCode = "401", description = "로그인이 필요한 서비스입니다.")
    })
    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody NoteDTO dto, HttpServletRequest request) {
        String loginId = (String) request.getSession().getAttribute("loginId");

        noteService.insert(dto, loginId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "쪽지 수정 API", description = "쪽지를 수정한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 작동입니다."),
            @ApiResponse(responseCode = "400", description = "입력한 데이터가 잘못되었습니다."),
            @ApiResponse(responseCode = "401", description = "로그인이 필요한 서비스입니다."),
            @ApiResponse(responseCode = "403", description = "해당 서비스는 작성자와 관리자만 사용 가능합니다.")
    })
    @PutMapping
    public ResponseEntity<Void> update(@RequestBody NoteDTO noteDTO, HttpServletRequest request) {
        String loginId = (String) request.getSession().getAttribute("loginId");

        noteService.update(noteDTO, loginId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "쪽지 삭제 API", description = "해당 id와 일치하는 쪽지를 삭제한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 작동입니다."),
            @ApiResponse(responseCode = "401", description = "로그인이 필요한 서비스입니다."),
            @ApiResponse(responseCode = "403", description = "해당 서비스는 작성자와 관리자만 사용 가능합니다.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNoteById(
            @Parameter(description = "쪽지 id")
            @PathVariable long id, HttpServletRequest request) {
        String loginId = (String) request.getSession().getAttribute("loginId");

        noteService.deleteById(id, loginId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "추천 등록 API", description = "해당 쪽지를 추천한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 작동입니다."),
            @ApiResponse(responseCode = "400", description = "해당 쪽지 혹은 유저가 존재하지 않습니다."),
            @ApiResponse(responseCode = "400", description = "이미 추천한 쪽지입니다."),
            @ApiResponse(responseCode = "401", description = "로그인이 필요한 서비스입니다.")
    })
    @PostMapping("/recommend")
    public ResponseEntity<Void> insert(@RequestBody NoteRecDTO dto, HttpServletRequest request) {
        String loginId = (String) request.getSession().getAttribute("loginId");

        noteRecService.insert(dto, loginId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "추천 취소 API", description = "해당 쪽지 추천을 취소한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 작동입니다."),
            @ApiResponse(responseCode = "400", description = "해당 쪽지 혹은 유저가 존재하지 않습니다."),
            @ApiResponse(responseCode = "400", description = "추천 취소는 추천을 해야만 가능합니다."),
            @ApiResponse(responseCode = "401", description = "로그인이 필요한 서비스입니다.")
    })
    @DeleteMapping("/recommend")
    public ResponseEntity<Void> delete(@RequestBody NoteRecDTO dto, HttpServletRequest request) {
        String loginId = (String) request.getSession().getAttribute("loginId");

        noteRecService.delete(dto, loginId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "쪽지 답글 조회 API", description = "해당 id를 가진 쪽지의 모든 댓글을 조회한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 작동입니다."),
            @ApiResponse(responseCode = "400", description = "해당 id를 가진 쪽지가 존재하지 않습니다.")
    })
    @GetMapping("/reply/{noteId}")
    public ResponseEntity<List<NoteReplyDTO>> selectByNoteId(
            @Parameter(description = "쪽지 id")
            @PathVariable long noteId) {
        return ResponseEntity.ok(noteReplyService.selectByNoteId(noteId));
    }

    @Operation(summary = "쪽지 답글 등록 API", description = "쪽지 답글을 입력한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 작동합니다."),
            @ApiResponse(responseCode = "400", description = "입력한 데이터가 잘못되었습니다."),
            @ApiResponse(responseCode = "401", description = "로그인이 필요한 서비스입니다.")
    })
    @PostMapping("/reply")
    public ResponseEntity<Void> insert(
            @RequestBody NoteReplyDTO dto, HttpServletRequest request) {
        String loginId = (String) request.getSession().getAttribute("loginId");

        noteReplyService.insert(dto, loginId);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "쪽지 답글 수정 API", description = "쪽지 답글을 수정한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 작동합니다."),
            @ApiResponse(responseCode = "400", description = "입력한 데이터가 잘못되었습니다."),
            @ApiResponse(responseCode = "401", description = "로그인이 필요한 서비스입니다."),
            @ApiResponse(responseCode = "403", description = "해당 서비스는 글쓴이, 혹은 관리자만 사용 가능합니다.")
    })
    @PutMapping("/reply")
    public ResponseEntity<Void> update(
            @RequestBody NoteReplyDTO dto, HttpServletRequest request) {
        String loginId = (String) request.getAttribute("loginId");

        noteReplyService.update(dto);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "쪽지 답글 삭제 API", description = "해당 id에 해당하는 쪽지 답글을 삭제한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 작동합니다."),
            @ApiResponse(responseCode = "401", description = "로그인이 필요한 서비스입니다."),
            @ApiResponse(responseCode = "403", description = "해당 서비스는 글쓴이, 혹은 관리자만 사용 가능합니다.")
    })
    @DeleteMapping("/reply/{id}")
    public ResponseEntity<Void> deleteReplyById(@PathVariable long id) {
        noteReplyService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
