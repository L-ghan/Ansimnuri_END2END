package com.end2end.ansimnuri.message.service;

import com.end2end.ansimnuri.message.dao.MessageRoomDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MessageRoomServiceImpl implements MessageRoomService {
    private final MessageRoomDAO messageRoomDAO;
}
