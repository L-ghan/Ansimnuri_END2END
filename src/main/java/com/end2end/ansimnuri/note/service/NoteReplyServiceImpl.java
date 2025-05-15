package com.end2end.ansimnuri.note.service;

import com.end2end.ansimnuri.member.domain.entity.Member;
import com.end2end.ansimnuri.member.domain.repository.MemberRepository;
import com.end2end.ansimnuri.note.dao.NoteReplyDAO;
import com.end2end.ansimnuri.note.domain.entity.Note;
import com.end2end.ansimnuri.note.domain.entity.NoteReply;
import com.end2end.ansimnuri.note.domain.repository.NoteReplyRepository;
import com.end2end.ansimnuri.note.domain.repository.NoteRepository;
import com.end2end.ansimnuri.note.dto.NoteReplyDTO;
import com.end2end.ansimnuri.util.exception.UnAuthenticationException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class NoteReplyServiceImpl implements NoteReplyService {
    private final NoteReplyRepository noteReplyRepository;
    private final NoteRepository noteRepository;
    private final MemberRepository memberRepository;

    @Override
    public List<NoteReplyDTO> selectByNoteId(long noteId) {
        return noteReplyRepository.findByNoteIdOrderById(noteId).stream()
                .map(NoteReplyDTO::of)
                .toList();
    }

    @Transactional
    @Override
    public void insert(NoteReplyDTO dto, String loginId) {
        Note note = noteRepository.findById(dto.getNoteId())
                        .orElseThrow(() -> new IllegalArgumentException("해당 id의 쪽지가 존재하지 않습니다."));
        Member member = memberRepository.findByLoginId(loginId)
                        .orElseThrow(() -> new IllegalArgumentException("해당 id의 유저가 없습니다."));

        noteReplyRepository.save(NoteReply.of(dto, note, member));
    }

    @Transactional
    @Override
    public void update(NoteReplyDTO dto, String loginId) {
        NoteReply noteReply = noteReplyRepository.findById(dto.getId())
                .orElseThrow(() -> new IllegalArgumentException("해당 id의 쪽지 댓글이 존재하지 않습니다."));

        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("해당 로그인 아이디는 존재하지 않습니다."));
        if(!member.getId().equals(noteReply.getMember().getId())) {
            throw new UnAuthenticationException();
        }

        noteReply.update(dto);
    }

    @Transactional
    @Override
    public void deleteById(long id, String loginId) {
        NoteReply noteReply = noteReplyRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 id의 쪽지 댓글이 존재하지 않습니다."));

        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("해당 로그인 아이디는 존재하지 않습니다."));
        if(!member.getId().equals(noteReply.getMember().getId())) {
            throw new UnAuthenticationException();
        }

        noteReplyRepository.delete(noteReply);
    }
}
