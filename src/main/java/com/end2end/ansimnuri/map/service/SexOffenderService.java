package com.end2end.ansimnuri.map.service;

import com.end2end.ansimnuri.map.dto.SexOffenderDTO;

import java.util.List;

public interface SexOffenderService {
    List<SexOffenderDTO> selectAll();
    void insert();
}
