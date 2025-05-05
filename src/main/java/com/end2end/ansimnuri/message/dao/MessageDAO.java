package com.end2end.ansimnuri.message.dao;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class MessageDAO {
    private final SqlSession mybatis;
}
