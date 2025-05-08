package com.end2end.ansimnuri.message.service;

import com.end2end.ansimnuri.message.dao.MessageBlockDAO;
import com.end2end.ansimnuri.message.domain.repository.MessageBlockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MessageBlockServiceImpl implements MessageBlockService {
    private final MessageBlockDAO messageBlockDAO;
    private final MessageBlockRepository messageBlockRepository;
}
