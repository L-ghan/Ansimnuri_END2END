package com.end2end.ansimnuri.board.dao;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class QnaReplyDAO {
    private final SqlSession mybatis;
}
