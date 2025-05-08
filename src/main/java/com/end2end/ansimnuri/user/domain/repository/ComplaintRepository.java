package com.end2end.ansimnuri.user.domain.repository;

import com.end2end.ansimnuri.user.domain.entity.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
}
