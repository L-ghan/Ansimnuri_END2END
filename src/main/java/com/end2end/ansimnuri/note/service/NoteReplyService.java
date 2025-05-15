package com.end2end.ansimnuri.note.service;

import com.end2end.ansimnuri.note.dto.NoteReplyDTO;

import java.util.List;

public interface NoteReplyService {
    List<NoteReplyDTO> selectByNoteId(long noteId);
    void insert(NoteReplyDTO dto, String loginId);
    void update(NoteReplyDTO dto, String loginId);
    void deleteById(long id, String loginId);
}
