package com.end2end.ansimnuri;

import com.end2end.ansimnuri.note.dao.NoteReplyDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NoteReplyServiceImpl implements NoteReplyService {
    private final NoteReplyDAO noteReplyDAO;
}
