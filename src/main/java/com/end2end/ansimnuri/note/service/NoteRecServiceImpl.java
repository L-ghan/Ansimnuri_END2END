package com.end2end.ansimnuri.note.service;

import com.end2end.ansimnuri.note.domain.repository.NoteRecRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NoteRecServiceImpl implements NoteRecService {
    private final NoteService noteService;
    private final NoteRecRepository noteRecRepository;
}
