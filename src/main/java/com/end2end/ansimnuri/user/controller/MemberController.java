package com.end2end.ansimnuri.user.controller;

import com.end2end.ansimnuri.user.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "유저 API", description = "유저 CRUD 기능을 가진 API")
@RequiredArgsConstructor
@RequestMapping("/api/member")
@RestController
public class MemberController {
    private final UserService userService;
}
