package com.end2end.ansimnuri.member.service;

import com.end2end.ansimnuri.member.dao.BlockDAO;
import com.end2end.ansimnuri.member.domain.repository.BlockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BlockServiceImpl implements BlockService {
    private final BlockDAO blockDAO;
    private final BlockRepository blockRepository;
}
