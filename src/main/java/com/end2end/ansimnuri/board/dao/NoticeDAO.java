package com.end2end.ansimnuri.board.dao;

import com.end2end.ansimnuri.board.dto.NoticeDTO;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class NoticeDAO {
    private final SqlSession mybatis;

    public List<NoticeDTO> selectAll(int start, int end) {
        Map<String, Integer> pram = Map.of("start", start, "end", end);
        return mybatis.selectList("notice.selectAll", pram);
    }

    public Optional<NoticeDTO> selectById(long id) {
        return Optional.ofNullable(mybatis.selectOne("notice.selectById", id));
    }

    public int countAll() {
        return mybatis.selectOne("notice.countAll");
    }

    public List<NoticeDTO> selectByTitleLike(String searchKey, int start, int end) {
        Map<String, Object> param = Map.of("searchKey", searchKey, "start", start, "end", end);
        return mybatis.selectList("notice.selectByTitleLike", param);
    }

    public int countByTitleLike(String searchKey) {
        return mybatis.selectOne("notice.countByTitleLike", searchKey);
    }

    public void insert( NoticeDTO noticeDTO) {
        mybatis.insert("notice.insert", noticeDTO);
    }

    public void update( NoticeDTO noticeDTO) {
        mybatis.update("notice.update", noticeDTO);
    }

    public void deleteById(long id) {
        mybatis.delete("notice.deleteById", id);
    }
}
