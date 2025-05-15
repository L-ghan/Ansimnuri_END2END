package com.end2end.ansimnuri.admin.service;

import com.end2end.ansimnuri.admin.dto.BlockDTO;

import java.util.List;

public interface BlockService {
    List<BlockDTO> selectAll();
    void insert(BlockDTO blockDTO);
    void deleteById(long id);
}
