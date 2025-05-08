package com.end2end.ansimnuri.message.domain.repository;

import com.end2end.ansimnuri.message.domain.entity.MessageBlock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageBlockRepository extends JpaRepository<MessageBlock, Long> {
}
