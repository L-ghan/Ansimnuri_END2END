package com.end2end.ansimnuri.admin.controller;

import com.end2end.ansimnuri.admin.dto.BlockDTO;
import com.end2end.ansimnuri.admin.dto.ComplaintDTO;
import com.end2end.ansimnuri.admin.service.BlockService;
import com.end2end.ansimnuri.admin.service.ComplaintService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "차단 API", description = "신고/차단 관련 기능을 모아두는 API")
@RequiredArgsConstructor
@RequestMapping("/api/admin/block")
@RestController
public class BlockController {
    private final BlockService blockService;
    private final ComplaintService complaintService;

    @GetMapping
    public ResponseEntity<List<BlockDTO>> selectAll() {

        return ResponseEntity.ok(blockService.selectAll());
    }

    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody BlockDTO dto) {
        blockService.insert(dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable long id) {
        blockService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/complaint")
    public ResponseEntity<List<ComplaintDTO>> selectAllComplaint() {
        return ResponseEntity.ok(complaintService.selectAll());
    }

    @GetMapping("/complaint/{id}")
    public ResponseEntity<ComplaintDTO> selectById(@PathVariable long id) {
        return ResponseEntity.ok(complaintService.selectById(id));
    }

    @PostMapping("/complaint")
    public ResponseEntity<Void> insert(@RequestBody ComplaintDTO dto, HttpServletRequest request) {
        String loginId = (String) request.getAttribute("loginId");

        complaintService.insert(dto, loginId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/complaint")
    public ResponseEntity<Void> submit() {
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteComplaint(@PathVariable long id) {
        complaintService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
