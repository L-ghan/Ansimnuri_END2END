package com.end2end.ansimnuri.message.controller;

import com.end2end.ansimnuri.message.service.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "메세지 API", description = "메세지 CURD, 메세지 차단 기능을 가진 API")
@RequiredArgsConstructor
@RequestMapping("/message")
@RestController
public class MessageController {
    private final MessageService messageService;
    private final MessageRoomService messageRoomService;
    private final MessageBlockService messageBlockService;
}
