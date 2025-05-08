package com.end2end.ansimnuri.member.dao;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ComplaintDAO {
    private final SqlSession mybatis;
}
