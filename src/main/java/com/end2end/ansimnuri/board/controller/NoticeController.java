package com.end2end.ansimnuri.board.controller;

import com.end2end.ansimnuri.board.dto.NoticeDTO;
import com.end2end.ansimnuri.board.service.NoticeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "공지사항 API", description = "공지사항 CRUD 관련 API")
@RequiredArgsConstructor
@RequestMapping("/notice")
@RestController
public class NoticeController {
    private final NoticeService noticeService;

    @Operation(summary = "전체 공지사항 조회 api", description = "모든 공지사항 내용을 가져온다.")
    @GetMapping()
    public ResponseEntity<List<NoticeDTO>> selectAll() {
        return ResponseEntity.ok(noticeService.selectAll());
    }

    @Operation(summary = "공지사항 조회 api", description = "해당 ID에 맞는 공지사항 내용을 가져온다.")
    @GetMapping("/{id}")
    public NoticeDTO selectById(
            @Parameter(description = "공지사항 id")
            @PathVariable Long id) {
        return null;
    }
}
