package com.end2end.ansimnuri.note.domain.repository;

import com.end2end.ansimnuri.note.domain.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoteRepository extends JpaRepository<Note, Long> {
}
