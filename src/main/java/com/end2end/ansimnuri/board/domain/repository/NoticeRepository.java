package com.end2end.ansimnuri.board.domain.repository;

import com.end2end.ansimnuri.board.domain.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
