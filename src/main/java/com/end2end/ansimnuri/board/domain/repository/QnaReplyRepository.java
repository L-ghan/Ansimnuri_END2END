package com.end2end.ansimnuri.board.domain.repository;

import com.end2end.ansimnuri.board.domain.entity.QnaReply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QnaReplyRepository extends JpaRepository<QnaReply, Long> {
    QnaReply findByQnaId(Long qnaId);
}
