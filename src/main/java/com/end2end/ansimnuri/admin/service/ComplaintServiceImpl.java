package com.end2end.ansimnuri.admin.service;

import com.end2end.ansimnuri.admin.dao.ComplaintDAO;
import com.end2end.ansimnuri.admin.domain.entity.Block;
import com.end2end.ansimnuri.admin.domain.entity.Complaint;
import com.end2end.ansimnuri.admin.domain.repository.BlockRepository;
import com.end2end.ansimnuri.admin.domain.repository.ComplaintRepository;
import com.end2end.ansimnuri.admin.dto.BlockDTO;
import com.end2end.ansimnuri.admin.dto.ComplaintDTO;
import com.end2end.ansimnuri.member.domain.entity.Member;
import com.end2end.ansimnuri.member.domain.repository.MemberRepository;
import com.end2end.ansimnuri.util.enums.Yn;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ComplaintServiceImpl implements ComplaintService {
    private final ComplaintDAO complaintDAO;
    private final ComplaintRepository complaintRepository;
    private final MemberRepository memberRepository;
    private final BlockRepository blockRepository;

    @Override
    public List<ComplaintDTO> selectAll() {
        return complaintRepository.findAll().stream()
                .map(ComplaintDTO::of)
                .toList();
    }

    @Transactional
    @Override
    public ComplaintDTO selectById(long id) {
        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("해당 id를 가진 신고 기록이 없습니다."));

        return ComplaintDTO.of(complaint);
    }

    @Transactional
    @Override
    public void insert(ComplaintDTO dto, String loginId) {
        Member reporter = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("해당 id를 가진 유저가 없습니다."));

        Member reportee = memberRepository.findById(dto.getReporteeId())
                .orElseThrow(() -> new IllegalArgumentException("해당 id를 가진 유저가 없습니다."));

        complaintRepository.save(Complaint.of(dto, reporter, reportee));
    }

    @Transactional
    @Override
    public void submit(long id, LocalDateTime blockEndDate) {
        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("해당 id를 가진 신고 기록이 없습니다."));
        complaint.submit(Yn.Y);

        BlockDTO blockDTO = BlockDTO.builder()
                .reason(complaint.getReason())
                .endDate(blockEndDate)
                .build();
        blockRepository.save(Block.of(blockDTO, complaint.getReportee()));
    }

    @Transactional
    @Override
    public void reject(long id) {
        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("해당 id를 가진 신고 기록이 없습니다."));
        complaint.submit(Yn.N);
    }

    @Transactional
    @Override
    public void deleteById(long id) {
        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("해당 id를 가진 신고 기록이 없습니다."));
        complaintRepository.delete(complaint);
    }
}
