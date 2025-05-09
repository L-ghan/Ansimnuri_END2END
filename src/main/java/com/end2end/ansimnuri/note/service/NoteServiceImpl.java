package com.end2end.ansimnuri.note.service;

import com.end2end.ansimnuri.member.domain.entity.Member;
import com.end2end.ansimnuri.member.domain.repository.MemberRepository;
import com.end2end.ansimnuri.note.dao.NoteDAO;
import com.end2end.ansimnuri.note.domain.entity.Note;
import com.end2end.ansimnuri.note.domain.repository.NoteRepository;
import com.end2end.ansimnuri.note.dto.NoteDTO;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NoteServiceImpl implements NoteService {
    private final NoteDAO noteDAO;
    private final NoteRepository noteRepository;
    private final MemberRepository memberRepository;

    @Transactional
    @Override
    public void insert(NoteDTO noteDTO) {
        Member member = memberRepository.findById(noteDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당하는 id의 유저가 없습니다."));
        noteRepository.save(Note.of(noteDTO, member));
    }

    @Transactional
    @Override
    public void update(NoteDTO noteDTO) {
        Note note = noteRepository.findById(noteDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당하는 id의 쪽지가 없습니다."));
        note.update(noteDTO);
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 id의 쪽지가 없습니다."));
        noteRepository.delete(note);
    }
}
