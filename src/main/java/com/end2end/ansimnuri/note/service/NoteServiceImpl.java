package com.end2end.ansimnuri.note.service;

import com.end2end.ansimnuri.member.domain.entity.Member;
import com.end2end.ansimnuri.member.domain.repository.MemberRepository;
import com.end2end.ansimnuri.note.domain.entity.Note;
import com.end2end.ansimnuri.note.domain.repository.NoteRepository;
import com.end2end.ansimnuri.note.dto.NoteDTO;
import com.end2end.ansimnuri.note.dto.NoteSocketDTO;
import com.end2end.ansimnuri.note.endpoint.NoteEndpoint;
import com.end2end.ansimnuri.util.enums.RequestType;
import com.end2end.ansimnuri.util.exception.UnAuthenticationException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class NoteServiceImpl implements NoteService {
    private final NoteRepository noteRepository;
    private final MemberRepository memberRepository;

    @Override
    public List<NoteDTO> selectAll() {
        return noteRepository.findAll().stream()
                .map(NoteDTO::of)
                .toList();
    }

    @Transactional
    @Override
    public void insert(NoteDTO dto, String loginId) {
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 id의 유저가 없습니다."));
        dto.setUserId(member.getId());

        Note note = noteRepository.save(Note.of(dto, member));
        NoteEndpoint.send(
                NoteSocketDTO.of(RequestType.POST, note.getMember().getId(), NoteDTO.of(note)));
    }

    @Transactional
    @Override
    public void update(NoteDTO noteDTO, String loginId) {
        Note note = noteRepository.findById(noteDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당하는 id의 쪽지가 없습니다."));

        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("해당 로그인 아이디는 존재하지 않습니다."));
        if(!member.getId().equals(note.getMember().getId())) {
            throw new UnAuthenticationException();
        }

        note.update(noteDTO);
        NoteEndpoint.send(
                NoteSocketDTO.of(RequestType.UPDATE, note.getMember().getId(), NoteDTO.of(note)));
    }

    @Transactional
    @Override
    public void deleteById(long id, String loginId) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당하는 id의 쪽지가 없습니다."));
        NoteDTO noteDTO = NoteDTO.of(note);

        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("해당 로그인 아이디는 존재하지 않습니다."));
        if(!member.getId().equals(note.getMember().getId())) {
            throw new UnAuthenticationException();
        }

        noteRepository.delete(note);
        NoteEndpoint.send(
                NoteSocketDTO.of(RequestType.DELETE, note.getMember().getId(), noteDTO));
    }
}
