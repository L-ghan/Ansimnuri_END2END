package com.end2end.ansimnuri.note.dao;

import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class NoteDAO {
    private final SqlSession mybatis;
}
