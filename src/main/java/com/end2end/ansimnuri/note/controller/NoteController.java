package com.end2end.ansimnuri;

import com.end2end.ansimnuri.note.service.NoteRecService;
import com.end2end.ansimnuri.note.service.NoteReplyService;
import com.end2end.ansimnuri.note.service.NoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/note")
@RestController
public class NoteController {
    private final NoteService noteService;
    private final NoteRecService noteRecService;
    private final NoteReplyService noteReplyService;
}
