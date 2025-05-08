package com.end2end.ansimnuri.note.service;

import com.end2end.ansimnuri.note.dao.NoteReplyDAO;
import com.end2end.ansimnuri.note.domain.repository.NoteReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NoteReplyServiceImpl implements NoteReplyService {
    private final NoteReplyDAO noteReplyDAO;
    private final NoteReplyRepository noteReplyRepository;
}
