package com.end2end.ansimnuri.admin.service;

import com.end2end.ansimnuri.admin.dto.ComplaintDTO;
import com.end2end.ansimnuri.util.enums.Yn;

import java.time.LocalDateTime;
import java.util.List;

public interface ComplaintService {
    List<ComplaintDTO> selectAll();
    ComplaintDTO selectById(long id);
    void insert(ComplaintDTO dto, String loginId);
    void submit(long id, LocalDateTime blockEndDate);
    void reject(long id);
    void deleteById(long id);
}
