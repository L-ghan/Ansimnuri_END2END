package com.end2end.ansimnuri;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class NoteRecDAO {
    private final SqlSession mybatis;
}
