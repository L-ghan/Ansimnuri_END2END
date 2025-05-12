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
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@Service
public class PoliceServiceImpl implements PoliceService {
    private final PoliceRepository policeRepository;

    @Value("${data.api.key}")
    private String SERVICE_KEY;
    @Value("${kakao.api.key}")
    private String kakaoApiKey;

    @Transactional
    @Override
    public void insert() {
        policeRepository.deleteAll();

        RestTemplate restTemplate = new RestTemplate();

        URI url = UriComponentsBuilder
                .fromUriString("https://api.odcloud.kr/api/15124966/v1/uddi:345a2432-5fee-4c49-a353-80b62496a43b")
                .queryParam("page", 1)
                .queryParam("perPage", 100)
                .queryParam("serviceKey", SERVICE_KEY)
                .encode()
                .build()
                .toUri();
        System.out.println(url);
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode root = mapper.readTree(response.getBody());
            JsonNode dataArray = root.path("data");

            for (JsonNode data : dataArray) {
                String name = data.path("경찰서명칭").asText();

                String location = data.path("위치").asText();
                if(location.equals("서울특별시")) {
                    String address = data.path("경찰서주소").asText();

                    url = UriComponentsBuilder
                            .fromUriString("https://dapi.kakao.com/v2/local/search/address.json")
                            .queryParam("query", address)
                            .encode()
                            .build()
                            .toUri();
                    HttpHeaders headers = new HttpHeaders();
                    headers.set("Authorization", "KakaoAK " + kakaoApiKey);

                    response = restTemplate
                            .exchange(url, HttpMethod.GET, new HttpEntity<>(null, headers), String.class);

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
                                .build();
                        policeRepository.save(Police.of(dto));
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.out.println(url);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
