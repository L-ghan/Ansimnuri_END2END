package com.end2end.ansimnuri.note.domain.repository;

import com.end2end.ansimnuri.note.domain.entity.NoteReply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteReplyRepository extends JpaRepository<NoteReply, Long> {
}
