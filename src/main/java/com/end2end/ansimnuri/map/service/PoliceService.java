package com.end2end.ansimnuri.map.service;

import com.end2end.ansimnuri.map.dto.PoliceDTO;

import java.util.List;

public interface PoliceService {
    List<PoliceDTO> selectAll();
    List<PoliceDTO> selectByAddressLike(String searchKey);
    PoliceDTO selectById(long id);
    void insert();
}
