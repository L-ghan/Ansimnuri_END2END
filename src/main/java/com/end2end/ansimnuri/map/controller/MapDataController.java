package com.end2end.ansimnuri.map.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "map data API", description = "맵 데이터에 관련한 기능을 가진 API")
@RequiredArgsConstructor
@RequestMapping("/map")
@RestController
public class MapDataController {
}
