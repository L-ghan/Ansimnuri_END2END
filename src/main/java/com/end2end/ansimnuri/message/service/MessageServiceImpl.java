package com.end2end.ansimnuri;

import com.end2end.ansimnuri.message.dao.MessageDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MessageServiceImpl implements MessageService{
    private final MessageDAO messageDAO;
}
