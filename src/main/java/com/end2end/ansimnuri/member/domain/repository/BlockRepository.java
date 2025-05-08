package com.end2end.ansimnuri.member.domain.repository;

import com.end2end.ansimnuri.member.domain.entity.Block;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockRepository extends JpaRepository<Block, Long> {
}
