package com.end2end.ansimnuri.member.domain.repository;

import com.end2end.ansimnuri.member.domain.entity.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
}
