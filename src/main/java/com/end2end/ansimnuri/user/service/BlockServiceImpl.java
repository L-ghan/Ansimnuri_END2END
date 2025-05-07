package com.end2end.ansimnuri.user.service;

import com.end2end.ansimnuri.user.dao.BlockDAO;
import com.end2end.ansimnuri.user.domain.repository.BlockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BlockServiceImpl implements BlockService {
    private final BlockDAO blockDAO;
    private final BlockRepository blockRepository;
}
