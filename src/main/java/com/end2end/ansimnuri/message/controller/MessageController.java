package com.end2end.ansimnuri.message.controller;

import com.end2end.ansimnuri.message.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/message")
@RestController
public class MessageController {
    private final MessageService messageService;
    private final MessageRoomService messageRoomService;
    private final MessageBlockService messageBlockService;
}
