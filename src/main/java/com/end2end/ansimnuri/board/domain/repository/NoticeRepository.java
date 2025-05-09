package com.end2end.ansimnuri.board.domain.repository;

import com.end2end.ansimnuri.board.domain.entity.Notice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
