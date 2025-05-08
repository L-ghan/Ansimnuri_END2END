package com.end2end.ansimnuri.board.domain.repository;

import com.end2end.ansimnuri.board.domain.entity.Qna;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QnaRepository extends JpaRepository<Qna, Long> {
}
