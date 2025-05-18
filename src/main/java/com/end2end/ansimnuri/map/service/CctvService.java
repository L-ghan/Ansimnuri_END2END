package com.end2end.ansimnuri.map.service;

import com.end2end.ansimnuri.map.dto.CctvDTO;

import java.util.List;

public interface CctvService {
    List<CctvDTO> selectAll();
    void insert();
}
