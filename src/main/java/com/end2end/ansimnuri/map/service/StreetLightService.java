package com.end2end.ansimnuri.map.service;

import com.end2end.ansimnuri.map.dto.StreetLightDTO;

import java.util.List;

public interface StreetLightService {
    List<StreetLightDTO> selectAll();
    void insert();
}
