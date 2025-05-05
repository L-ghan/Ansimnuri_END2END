package com.end2end.ansimnuri;

import com.end2end.ansimnuri.message.dao.MessageBlockDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MessageBlockServiceImpl implements MessageBlockService {
    private final MessageBlockDAO messageBlockDAO;
}
