package com.end2end.ansimnuri.note.service;

import com.end2end.ansimnuri.note.dao.NoteDAO;
import com.end2end.ansimnuri.note.domain.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NoteServiceImpl implements NoteService {
    private final NoteDAO noteDAO;
    private final NoteRepository noteRepository;
}
