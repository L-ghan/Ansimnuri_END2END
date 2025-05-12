package com.end2end.ansimnuri.chatbot.dao;

import com.end2end.ansimnuri.chatbot.dto.PoliceDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ChatDao {

    @Autowired
    private SqlSession mybatis;

    public List<PoliceDto> findAllPolice() {
        return mybatis.selectList("chatbot.selectAllPolice");
    }
}

