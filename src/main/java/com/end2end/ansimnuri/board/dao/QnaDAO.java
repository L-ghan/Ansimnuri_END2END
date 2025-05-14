package com.end2end.ansimnuri.board.dao;

import com.end2end.ansimnuri.board.dto.QnaDTO;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class QnaDAO {
    private final SqlSession mybatis;

    public List<QnaDTO> selectAll(int start, int end) {
        Map<String, Integer> paramMap = Map.of("start", start, "end", end);
        return mybatis.selectList("qna.selectAll", paramMap);
    }

    public List<QnaDTO> selectByUserId(long userId, int start, int end) {
        Map<String, Object> paramMap = Map.of("userId", userId, "start", start, "end", end);
        return mybatis.selectList("qna.selectByUserId", paramMap);
    }

    public Optional<QnaDTO> selectById(long id) {
        return Optional.ofNullable(mybatis.selectOne("qna.selectById", id));
    }

    public int countAll() {
        return mybatis.selectOne("qna.countAll");
    }

    public int countByUserId(long userId) {
        return mybatis.selectOne("qna.countByUserId", userId);
    }

    public void insert(QnaDTO qnaDTO) {
        mybatis.insert("qna.insert", qnaDTO);
    }

    public void update(QnaDTO qnaDTO) {
        mybatis.update("qna.update", qnaDTO);
    }

    public void deleteById(long id) {
        mybatis.delete("qna.deleteById", id);
    }
}
