package com.end2end.ansimnuri;

import com.end2end.ansimnuri.board.dao.NoticeDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NoticeServiceImpl implements NoticeService {
    private final NoticeDAO noticeDAO;
}
