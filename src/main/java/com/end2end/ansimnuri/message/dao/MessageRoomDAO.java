package com.end2end.ansimnuri.message.dao;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class MessageRoomDAO {
    private final SqlSession mybatis;
}
