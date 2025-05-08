package com.end2end.ansimnuri.member.controller;

import com.end2end.ansimnuri.member.service.BlockService;
import com.end2end.ansimnuri.member.service.ComplaintService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "차단 API", description = "유저 신고 처리 및 유저 차단을 하는 API")
@RequiredArgsConstructor
@RequestMapping("/block")
@RestController
public class BlockController {
    private final BlockService blockService;
    private final ComplaintService complaintService;
}
