package com.end2end.ansimnuri.note.domain.repository;

import com.end2end.ansimnuri.member.domain.entity.Member;
import com.end2end.ansimnuri.note.domain.entity.Note;
import com.end2end.ansimnuri.note.domain.entity.NoteRec;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NoteRecRepository extends JpaRepository<NoteRec, Long> {
    Integer countByNote(Note note);
    Optional<NoteRec> findByNoteAndMember(Note note, Member member);
}
