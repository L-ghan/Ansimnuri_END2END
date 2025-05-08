package com.end2end.ansimnuri.note.domain.repository;

import com.end2end.ansimnuri.note.domain.entity.NoteRec;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRecRepository extends JpaRepository<NoteRec, Long> {
}
