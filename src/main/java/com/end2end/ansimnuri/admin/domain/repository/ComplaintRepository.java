package com.end2end.ansimnuri.admin.domain.repository;

import com.end2end.ansimnuri.admin.domain.entity.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
}
