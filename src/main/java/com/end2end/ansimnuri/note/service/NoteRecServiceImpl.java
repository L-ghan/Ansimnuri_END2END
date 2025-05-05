package com.end2end.ansimnuri;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NoteRecServiceImpl implements NoteRecService {
    private final NoteService noteService;
}
