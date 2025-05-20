package com.end2end.ansimnuri.map.service;

import com.end2end.ansimnuri.map.domain.entity.Police;
import com.end2end.ansimnuri.map.domain.repository.PoliceRepository;
import com.end2end.ansimnuri.map.dto.PoliceDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PoliceServiceImpl implements PoliceService {
    private final PoliceRepository policeRepository;

    @Value("${data.api.key}")
    private String dataApiKey;
    @Value("${kakao.api.key}")
    private String kakaoApiKey;

    @Override
    public List<PoliceDTO> selectAll() {
        return policeRepository.findAll().stream()
                .map(PoliceDTO::of)
                .toList();
    }

    @Override
    public List<PoliceDTO> selectByAddressLike(String searchKey) {
        return policeRepository.findByAddressContaining(searchKey).stream()
                .map(PoliceDTO::of)
                .toList();
    }

    @Override
    public List<PoliceDTO> selectByType(String type) {
        return policeRepository.findByType(type).stream()
                .map(PoliceDTO::of)
                .toList();
    }

    @Override
    public PoliceDTO selectById(long id) {
        Police police = policeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 id의 정보는 존재하지 않습니다."));

        return PoliceDTO.of(police);
    }

    @Scheduled(cron = "0 0 0 1 * *")
    @Transactional
    @Override
    public void insert() {
        policeRepository.deleteAll();

        RestTemplate restTemplate = new RestTemplate();

        String url = "https://api.odcloud.kr/api/15077036/v1/uddi:9f06486e-514f-45c9-a531-f15d74dba23e"
                + "?page=1&perPage=243&serviceKey=" + dataApiKey;
        ResponseEntity<String> response = restTemplate.getForEntity(URI.create(url), String.class);
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());
            JsonNode dataArray = root.path("data");

            for (JsonNode data : dataArray) {
                String name = data.path("경찰서").asText();
                String type = data.path("구분").asText();

                String address = data.path("주소").asText();
                URI uri = UriComponentsBuilder
                        .fromUriString("https://dapi.kakao.com/v2/local/search/address.json")
                        .queryParam("query", address)
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
                    JsonNode target = documents.path(0);

                    double latitude = target.path("x").asDouble();
                    double longitude = target.path("y").asDouble();

                    PoliceDTO dto = PoliceDTO.builder()
                            .name(name)
                            .address(address)
                            .longitude(longitude)
                            .latitude(latitude)
                            .type(type)
                            .build();
                    policeRepository.save(Police.of(dto));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
