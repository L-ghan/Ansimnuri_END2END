package com.end2end.ansimnuri.message.domain.repository;

import com.end2end.ansimnuri.message.domain.entity.MessageRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRoomRepository extends JpaRepository<MessageRoom, Long> {
}
