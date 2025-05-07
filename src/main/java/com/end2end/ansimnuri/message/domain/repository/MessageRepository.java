package com.end2end.ansimnuri.message.domain.repository;

import com.end2end.ansimnuri.message.domain.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
