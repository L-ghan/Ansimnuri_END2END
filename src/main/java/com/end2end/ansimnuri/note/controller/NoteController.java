package com.end2end.ansimnuri.note.controller;

import com.end2end.ansimnuri.note.service.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name="쪽지 API", description = "쪽지 CRUD, 쪽지 추천, 쪽지 답글 CRUD 기능을 가진 API")
@RequiredArgsConstructor
@RequestMapping("/api/note")
@RestController
public class NoteController {
    private final NoteService noteService;
    private final NoteRecService noteRecService;
    private final NoteReplyService noteReplyService;
}
