package com.end2end.ansimnuri.note.service;

import com.end2end.ansimnuri.note.dto.NoteDTO;

public interface NoteService {
    void insert(NoteDTO noteDTO);
    void update(NoteDTO noteDTO);
    void deleteById(long id);
}
