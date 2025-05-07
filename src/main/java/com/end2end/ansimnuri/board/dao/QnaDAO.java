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
        return mybatis.selectList("board.qna.selectAll", paramMap);
    }

    public List<QnaDTO> selectByUserId(long userId, int start, int end) {
        Map<String, Object> paramMap = Map.of("userId", userId, "start", start, "end", end);
        return mybatis.selectList("board.qna.selectByUserId", paramMap);
    }

    public Optional<QnaDTO> selectById(long id) {
        return Optional.ofNullable(QnaDTO.builder().build());
    }

    public int countAll() {
        return mybatis.selectOne("board.qna.countAll");
    }

    public int countByUserId(long userId) {
        return mybatis.selectOne("board.qna.countByUserId", userId);
    }

    public void insert(QnaDTO qnaDTO) {
        mybatis.insert("board.qna.insert", qnaDTO);
    }

    public void update(QnaDTO qnaDTO) {
        mybatis.update("board.qna.update", qnaDTO);
    }

    public void deleteById(long id) {
        mybatis.delete("board.qna.deleteById", id);
    }
}
