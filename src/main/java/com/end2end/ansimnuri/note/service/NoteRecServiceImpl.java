package com.end2end.ansimnuri.note.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NoteRecServiceImpl implements NoteRecService {
    private final NoteService noteService;
}
