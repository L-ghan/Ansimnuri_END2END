package com.end2end.ansimnuri.admin.domain.repository;

import com.end2end.ansimnuri.admin.domain.entity.Block;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlockRepository extends JpaRepository<Block, Long> {
}
