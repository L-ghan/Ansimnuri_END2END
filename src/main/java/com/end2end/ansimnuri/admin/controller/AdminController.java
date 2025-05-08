package com.end2end.ansimnuri.admin.controller;

import com.end2end.ansimnuri.admin.service.BlockService;
import com.end2end.ansimnuri.admin.service.ComplaintService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "관리자 API", description = "관리자가 사용하는 기능을 모아두는 API")
@RequiredArgsConstructor
@RequestMapping("/api/admin")
@RestController
public class AdminController {
    private final BlockService blockService;
    private final ComplaintService complaintService;
}
