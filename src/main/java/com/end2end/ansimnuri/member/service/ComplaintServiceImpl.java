package com.end2end.ansimnuri.member.service;

import com.end2end.ansimnuri.member.dao.ComplaintDAO;
import com.end2end.ansimnuri.member.domain.repository.ComplaintRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ComplaintServiceImpl implements ComplaintService {
    private final ComplaintDAO complaintDAO;
    private final ComplaintRepository complaintRepository;
}
