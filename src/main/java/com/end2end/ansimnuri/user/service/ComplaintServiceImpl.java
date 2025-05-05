package com.end2end.ansimnuri.user.service;

import com.end2end.ansimnuri.user.dao.ComplaintDAO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ComplaintServiceImpl implements ComplaintService {
    private final ComplaintDAO complaintDAO;
}
