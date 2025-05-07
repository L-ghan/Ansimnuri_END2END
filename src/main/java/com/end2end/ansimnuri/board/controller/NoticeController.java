package com.end2end.ansimnuri.board.controller;

import com.end2end.ansimnuri.board.dto.NoticeDTO;
import com.end2end.ansimnuri.board.service.NoticeService;
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

@Tag(name = "공지사항 API", description = "공지사항 관련 기능을 가진 API")
@RequiredArgsConstructor
@RequestMapping("/notice")
@RestController
public class NoticeController {
    private final NoticeService noticeService;

    @Operation(summary = "전체 공지사항 조회 api", description = "해당 페이지에 해당하는 공지사항 내용을 가져온다.")
    @ApiResponse(responseCode = "200", description = "정상 작동입니다.")
    @GetMapping
    public ResponseEntity<List<NoticeDTO>> selectAll(
            @Parameter(description = "페이지")
            @RequestParam(defaultValue = "1") int page) {
        return ResponseEntity.ok(noticeService.selectAll(page));
    }

    @Operation(summary = "공지사항 제목 검색 api",
            description = "해당 검색 내용이 포함된 제목을 가진 공지사항 내용을 가져온다.")
    @Parameters({
            @Parameter(name="searchKey", description = "검색 내용"),
            @Parameter(name = "page", description = "페이지")
    })
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 작동입니다."),
            @ApiResponse(responseCode = "404", description = "잘못된 페이지 번호입니다.")
    })
    @GetMapping("/search")
    public ResponseEntity<List<NoticeDTO>> selectByTitle(String searchKey, int page) {
        return ResponseEntity.ok(noticeService.selectByTitleLike(searchKey, page));
    }

    @Operation(summary = "공지사항 조회 api", description = "해당 ID에 맞는 공지사항 내용을 가져온다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 작동입니다."),
            @ApiResponse(responseCode = "404", description = "해당 id의 공지사항이 존재하지 않습니다.")  // TODO: 에러 응답을 보여줄 것
    })
    @GetMapping("/{id}")
    public ResponseEntity<NoticeDTO> selectById(
            @Parameter(description = "공지사항 id")
            @PathVariable Long id) {
        return ResponseEntity.ok(noticeService.selectById(id));
    }

    @Operation(summary = "공지사항 등록 api", description = "공지사항을 등록한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 작동입니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 입력값을 넣었습니다."),
            @ApiResponse(responseCode = "403", description = "해당 요청은 관리자만 사용 가능합니다.")
    })
    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody NoticeDTO noticeDTO) {
        noticeService.insert(noticeDTO);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "공지사항 수정 api", description = "공지사항을 수정한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 작동입니다."),
            @ApiResponse(responseCode = "400", description = "잘못된 입력값을 넣었습니다."),
            @ApiResponse(responseCode = "403", description = "해당 요청은 관리자만 사용 가능합니다.")
    })
    @PutMapping
    public ResponseEntity<Void> update(@RequestBody NoticeDTO noticeDTO) {
        noticeService.update(noticeDTO);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "공지사항 삭제 api", description = "해당 ID에 맞는 공지사항을 삭제한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 작동입니다."),
            @ApiResponse(responseCode = "400", description = "해당 id의 공지사항이 존재하지 않습니다."),  // TODO: 에러 응답을 보여줄 것
            @ApiResponse(responseCode = "403", description = "해당 요청은 관리자만 사용 가능합니다.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "공지사항 id")
            @PathVariable Long id) {
        noticeService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
