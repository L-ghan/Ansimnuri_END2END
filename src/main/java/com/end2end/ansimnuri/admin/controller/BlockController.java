package com.end2end.ansimnuri.admin.controller;

import com.end2end.ansimnuri.admin.dto.BlockDTO;
import com.end2end.ansimnuri.admin.service.BlockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "차단 API", description = "차단 관련 기능을 모아두는 API")
@RequiredArgsConstructor
@RequestMapping("/api/admin/block")
@RestController
public class BlockController {
    private final BlockService blockService;

    @Operation(summary = "차단 목록 조회 API", description = "모든 차단 목록을 조회한다.")
    @ApiResponse(responseCode = "200", description = "정상 작동입니다.")
    @GetMapping
    public ResponseEntity<List<BlockDTO>> selectAll() {
        return ResponseEntity.ok(blockService.selectAll());
    }

    @Operation(summary = "차단 등록 API", description = "차단을 등록한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 작동입니다."),
            @ApiResponse(responseCode = "400", description = "입력 데이터가 잘못되었습니다."),
            @ApiResponse(responseCode = "401", description = "해당 서비스는 로그인이 필요합니다."),
            @ApiResponse(responseCode = "403", description = "해당 서비스는 관리자만이 사용할 수 있습니다.")
    })
    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody BlockDTO dto) {
        blockService.insert(dto);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "차단 해제 API", description = "차단을 해제한다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 작동입니다."),
            @ApiResponse(responseCode = "400", description = "입력 데이터가 잘못되었습니다."),
            @ApiResponse(responseCode = "401", description = "해당 서비스는 로그인이 필요합니다."),
            @ApiResponse(responseCode = "403", description = "해당 서비스는 관리자만이 사용할 수 있습니다.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable long id) {
        blockService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
