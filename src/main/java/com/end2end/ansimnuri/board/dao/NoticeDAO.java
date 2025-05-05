package com.end2end.ansimnuri.board.dao;

import com.end2end.ansimnuri.board.dto.NoticeDTO;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class NoticeDAO {
    private final SqlSession mybatis;

    public List<NoticeDTO> selectAll() {
        return null;
    }

    public Optional<NoticeDTO> selectById(long id) {
        return Optional.ofNullable(NoticeDTO.builder().build());
    }
}
