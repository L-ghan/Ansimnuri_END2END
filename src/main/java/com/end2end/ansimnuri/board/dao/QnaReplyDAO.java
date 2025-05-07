package com.end2end.ansimnuri.board.dao;

import com.end2end.ansimnuri.board.dto.QnaReplyDTO;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class QnaReplyDAO {
    private final SqlSession mybatis;

    public QnaReplyDTO selectByQnaId(long qnaId) {
        return mybatis.selectOne("selectByQnaId", qnaId);
    }

    public void insert(QnaReplyDTO qnaReplyDTO) {
        mybatis.insert("insert", qnaReplyDTO);
    }

    public void update(QnaReplyDTO qnaReplyDTO) {
        mybatis.update("update", qnaReplyDTO);
    }

    public void deleteById(long id) {
        mybatis.delete("deleteById", id);
    }
}
