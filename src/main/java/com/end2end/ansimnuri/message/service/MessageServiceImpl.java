package com.end2end.ansimnuri.message.service;

import com.end2end.ansimnuri.message.dao.MessageDAO;
import com.end2end.ansimnuri.message.domain.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MessageServiceImpl implements MessageService{
    private final MessageDAO messageDAO;
    private final MessageRepository messageRepository;
}
