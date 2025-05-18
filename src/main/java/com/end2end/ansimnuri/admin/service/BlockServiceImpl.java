package com.end2end.ansimnuri.admin.service;

import com.end2end.ansimnuri.admin.dao.BlockDAO;
import com.end2end.ansimnuri.admin.domain.entity.Block;
import com.end2end.ansimnuri.admin.domain.repository.BlockRepository;
import com.end2end.ansimnuri.admin.dto.BlockDTO;
import com.end2end.ansimnuri.member.domain.entity.Member;
import com.end2end.ansimnuri.member.domain.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BlockServiceImpl implements BlockService {
    private final BlockRepository blockRepository;
    private final MemberRepository memberRepository;

    @Override
    public List<BlockDTO> selectAll() {
        return blockRepository.findAll().stream()
                .map(BlockDTO::of)
                .toList();
    }

    @Transactional
    @Override
    public void insert(BlockDTO dto) {
        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("해당 id는 존재하지 않습니다."));

        blockRepository.save(Block.of(dto, member));
    }

    @Override
    public void deleteById(long id) {
        Block block = blockRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("해당 id를 가진 차단 기록이 존재하지 않습니다."));

        blockRepository.delete(block);
    }
}
