package com.end2end.ansimnuri.user.domain.repository;

import com.end2end.ansimnuri.user.domain.entity.Block;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockRepository extends JpaRepository<Block, Long> {
}
