package com.end2end.ansimnuri.note.service;

import com.end2end.ansimnuri.note.dto.NoteDTO;

import java.util.List;

public interface NoteService {
    List<NoteDTO> selectAll();
    void insert(NoteDTO noteDTO);
    void update(NoteDTO noteDTO);
    void deleteById(long id);
}
