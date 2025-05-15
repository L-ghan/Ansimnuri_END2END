package com.end2end.ansimnuri.chatbot.dao;

import com.end2end.ansimnuri.chatbot.dto.PoliceDTO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ChatDAO {

    @Autowired
    private SqlSession mybatis;

    public List<PoliceDTO> findPoliceByLocation(String keyword) {
        return mybatis.selectList("chatbot.findPoliceByKeyword", "%" + keyword + "%");
    }

    public String findGuideAnswer(String question) {
        return mybatis.selectOne("chatbot.findGuideAnswer", question);
    }

    public String findSupportAnswer(String question) {
        return mybatis.selectOne("chatbot.findSupportAnswer", question);
    }

    public String findFAQAnswer(String question) {
        return mybatis.selectOne("chatbot.findFAQAnswer", question);
    }


}
