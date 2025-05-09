package com.end2end.ansimnuri.note.service;

import com.end2end.ansimnuri.note.dto.NoteRecDTO;

public interface NoteRecService {
    void insert(NoteRecDTO dto);
    void deleteById(long id);
}
