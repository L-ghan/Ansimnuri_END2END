package com.end2end.ansimnuri.map.service;

import com.end2end.ansimnuri.map.domain.entity.SexOffender;
import com.end2end.ansimnuri.map.domain.entity.StreetLight;
import com.end2end.ansimnuri.map.domain.repository.StreetLightRepository;
import com.end2end.ansimnuri.map.dto.SexOffenderDTO;
import com.end2end.ansimnuri.map.dto.StreetLightDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class StreetLightServiceImpl implements StreetLightService {
    private final StreetLightRepository streetLightRepository;
    @Value("${public.data.api.key}")
    private String streetLightKeyApi;
    @Value("${kakao.api.key}")
    private String kakaoApiKey;

    @Override
    public List<StreetLightDTO> selectAll() {
        return streetLightRepository.findAll().stream()
                .map(StreetLightDTO::of)
                .toList();
    }

    @Transactional
    @Override
    public void insert() {
        streetLightRepository.deleteAll();

        List<StreetLight> streetLightList = new ArrayList<>();

        RestTemplate restTemplate = new RestTemplate();
        for (int i = 1; i <= 90; i++) {
            String url = "https://api.odcloud.kr/api/15107934/v1/uddi:20b10130-21ed-43f3-8e58-b8692fb8a2ff?page=" + i
                    + "&perPage=1000&returnType=json&serviceKey=" + streetLightKeyApi;

            ResponseEntity<String> response = restTemplate.getForEntity(URI.create(url), String.class);
            try {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = mapper.readTree(response.getBody());
                JsonNode dataArray = root.path("data");

                for (JsonNode data : dataArray) {
                    double latitude = data.path("위도").asDouble();
                    double longitude = data.path("경도").asDouble();
                    String managementNo = data.path("관리번호").asText();

                    URI uri = UriComponentsBuilder
                            .fromUriString("https://dapi.kakao.com/v2/local/geo/coord2address.json")
                            .queryParam("x", longitude)
                            .queryParam("y", latitude)
                            .encode()
                            .build()
                            .toUri();
                    HttpHeaders headers = new HttpHeaders();
                    headers.set("Authorization", "KakaoAK " + kakaoApiKey);

                    response = restTemplate
                            .exchange(uri, HttpMethod.GET, new HttpEntity<>(null, headers), String.class);

                    try {
                        JsonNode jsonNode = mapper.readTree(response.getBody());
                        JsonNode documents = jsonNode.path("documents");
                        JsonNode target = documents.path(0).path("address");

                        String city = target.path("region_1depth_name").asText();
                        if (!city.equals("서울")) {
                            break;
                        }

                        StreetLightDTO dto = StreetLightDTO.builder()
                                .latitude(latitude)
                                .longitude(longitude)
                                .managementNo(managementNo)
                                .address(target.path("address_name").asText())
                                .build();
                        streetLightList.add(StreetLight.of(dto));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        streetLightRepository.saveAll(streetLightList);
    }
}
