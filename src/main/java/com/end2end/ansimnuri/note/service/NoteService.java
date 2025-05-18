package com.end2end.ansimnuri.note.service;

import com.end2end.ansimnuri.note.dto.NoteDTO;

import java.util.List;

public interface NoteService {
    List<NoteDTO> selectAll();
    void insert(NoteDTO dto, String loginId);
    void update(NoteDTO dto, String loginId);
    void deleteById(long id, String loginId);
}
