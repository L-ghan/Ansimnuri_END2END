package com.end2end.ansimnuri.admin.dao;

import com.end2end.ansimnuri.admin.dto.BlockDTO;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Repository
public class BlockDAO {
    private final SqlSession mybatis;

    public List<BlockDTO> selectAll() {
        // 쿼리 아이디는 "namespace.id" 형식이어야 합니다.
        // 예를 들어 namespace가 "com.end2end.ansimnuri.admin.mapper.BlockMapper"라면
        // 아래처럼 작성
        List<Map<String, Object>> resultMaps = mybatis.selectList("com.end2end.ansimnuri.admin.mapper.BlockMapper.selectBlockWithLoginId");

        List<BlockDTO> blocks = new ArrayList<>();

        for (Map<String, Object> row : resultMaps) {
            BlockDTO dto = new BlockDTO();

            dto.setId(((Number) row.get("block_id")).longValue());
            dto.setMemberId(((Number) row.get("member_id")).longValue());
            dto.setReason((String) row.get("reason"));
            dto.setEndDate((LocalDateTime) row.get("end_date"));
            dto.setRegDate((LocalDateTime) row.get("reg_date"));
            // loginId는 DTO에 없으므로 필요하면 따로 처리하세요

            blocks.add(dto);
        }

        return blocks;
    }

}
