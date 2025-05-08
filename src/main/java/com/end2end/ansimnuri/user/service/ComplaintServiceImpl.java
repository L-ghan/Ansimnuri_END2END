package com.end2end.ansimnuri.user.service;

import com.end2end.ansimnuri.user.dao.ComplaintDAO;
import com.end2end.ansimnuri.user.domain.repository.ComplaintRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ComplaintServiceImpl implements ComplaintService {
    private final ComplaintDAO complaintDAO;
    private final ComplaintRepository complaintRepository;
}
