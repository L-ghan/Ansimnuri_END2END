package com.end2end.ansimnuri.map.service;

import com.end2end.ansimnuri.map.domain.repository.PoliceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PoliceServiceImpl implements PoliceService {
    private PoliceRepository policeRepository;
}
