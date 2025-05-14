package com.end2end.ansimnuri.board.service;

import com.end2end.ansimnuri.board.dao.QnaDAO;
import com.end2end.ansimnuri.board.domain.entity.Qna;
import com.end2end.ansimnuri.board.domain.repository.QnaRepository;
import com.end2end.ansimnuri.board.dto.QnaDTO;
import com.end2end.ansimnuri.member.domain.entity.Member;
import com.end2end.ansimnuri.member.domain.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.end2end.ansimnuri.util.Statics.RECORD_COUNT_PER_PAGE;

@RequiredArgsConstructor
@Service
public class QnaServiceImpl implements QnaService {
    private final QnaDAO qnaDAO;
    private final QnaRepository qnaRepository;
    private final MemberRepository memberRepository;

    @Override
    public List<QnaDTO> selectAll(int page) {
        int start = (page - 1) * 10;
        int end = Math.min(page * RECORD_COUNT_PER_PAGE, qnaDAO.countAll());

        return qnaDAO.selectAll(start, end);
    }

    @Override
    public List<QnaDTO> selectByUserId(long userId, int page) {
        int start = (page - 1) * 10;
        int end = Math.min(page * RECORD_COUNT_PER_PAGE, qnaDAO.countByUserId(userId));

        return qnaDAO.selectByUserId(userId, start, end);
    }

    @Override
    public QnaDTO selectById(long id) {
        Qna qna = qnaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("%d에 해당하는 ID가 존재하지 않습니다.", id)));
        return QnaDTO.of(qna);
    }

    @Transactional
    @Override
    public void insert(QnaDTO qnaDTO, String loginId) {
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("%s에 해당하는 ID가 존재하지 않습니다.", loginId)));
        qnaDTO.setUserId(member.getId());

        qnaRepository.save(Qna.of(member, qnaDTO.getTitle(), qnaDTO.getContent()));
    }

    @Transactional
    @Override
    public void update(QnaDTO qnaDTO) {
        Qna qna = qnaRepository.findById(qnaDTO.getId())
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("%d에 해당하는 ID가 존재하지 않습니다.", qnaDTO.getId())));
        qna.update(qnaDTO.getTitle(), qnaDTO.getContent());
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        Qna qna = qnaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("%d에 해당하는 ID가 존재하지 않습니다.", id)));
        qnaRepository.delete(qna);
    }
}
