package com.end2end.ansimnuri.admin.service;

import com.end2end.ansimnuri.admin.dao.ComplaintDAO;
import com.end2end.ansimnuri.admin.domain.repository.ComplaintRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ComplaintServiceImpl implements ComplaintService {
    private final ComplaintDAO complaintDAO;
    private final ComplaintRepository complaintRepository;
}
