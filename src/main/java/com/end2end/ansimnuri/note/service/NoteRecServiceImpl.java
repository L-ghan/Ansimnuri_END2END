package com.end2end.ansimnuri.note.service;

import com.end2end.ansimnuri.member.domain.entity.Member;
import com.end2end.ansimnuri.member.domain.repository.MemberRepository;
import com.end2end.ansimnuri.note.domain.entity.Note;
import com.end2end.ansimnuri.note.domain.entity.NoteRec;
import com.end2end.ansimnuri.note.domain.repository.NoteRecRepository;
import com.end2end.ansimnuri.note.domain.repository.NoteRepository;
import com.end2end.ansimnuri.note.dto.NoteRecDTO;
import com.end2end.ansimnuri.util.exception.UnAuthenticationException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NoteRecServiceImpl implements NoteRecService {
    private final NoteRecRepository noteRecRepository;
    private final MemberRepository memberRepository;
    private final NoteRepository noteRepository;

    @Transactional
    @Override
    public void insert(NoteRecDTO dto, String loginId) {
        Note note = noteRepository.findById(dto.getNoteId())
                .orElseThrow(() -> new IllegalArgumentException("해당 id에 해당하는 쪽지가 없습니다."));
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("해당 id를 가진 유저가 없습니다."));

        NoteRec noteRec = noteRecRepository.findByNoteAndMember(note, member)
                .orElse(null);

        if(noteRec != null) {
            throw new IllegalArgumentException("이미 추천한 쪽지입니다.");
        }

        noteRecRepository.save(NoteRec.of(note, member));
    }

    @Transactional
    @Override
    public void delete(NoteRecDTO dto, String loginId) {
        Note note = noteRepository.findById(dto.getNoteId())
                .orElseThrow(() -> new IllegalArgumentException("해당 id에 해당하는 쪽지가 없습니다."));
        Member member = memberRepository.findById(dto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("해당 id를 가진 유저가 없습니다."));
        if(member.getId().equals(dto.getUserId())) {
            throw new UnAuthenticationException();
        }

        NoteRec noteRec = noteRecRepository.findByNoteAndMember(note, member)
                .orElseThrow(() -> new IllegalArgumentException("해당 id를 가진 추천 내역이 없습니다."));
        noteRecRepository.delete(noteRec);
    }
}
