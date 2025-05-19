package com.end2end.ansimnuri.map.service;

import com.end2end.ansimnuri.map.domain.entity.Police;
import com.end2end.ansimnuri.map.domain.entity.SexOffender;
import com.end2end.ansimnuri.map.domain.repository.SexOffenderRepository;
import com.end2end.ansimnuri.map.dto.PoliceDTO;
import com.end2end.ansimnuri.map.dto.SexOffenderDTO;
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
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SexOffenderServiceImpl implements SexOffenderService {
    private final SexOffenderRepository sexOffenderRepository;

    @Value("${sexoffender.api.key}")
    private String sexOffenderKeyApi;
    @Value("${kakao.api.key}")
    private String kakaoApiKey;

    @Override
    public List<SexOffenderDTO> selectAll() {
        return sexOffenderRepository.findAll().stream()
                .map(SexOffenderDTO::of)
                .toList();
    }

    @Scheduled(cron = "0 0 0 * 1 *")
    @Transactional
    @Override
    public void insert() {
        sexOffenderRepository.deleteAll();

        List<SexOffender> sexOffenderList = new ArrayList<>();

        RestTemplate restTemplate = new RestTemplate();
        for(int i = 1; i <= 20; i++) {
            String url = "https://apis.data.go.kr/1383000/sais/SexualAbuseNoticeAddrService/getSexualAbuseNoticeAddrList?pageNo=" + i
                    + "&numOfRows=1000&type=json&serviceKey=" + sexOffenderKeyApi;

            ResponseEntity<String> response = restTemplate.getForEntity(URI.create(url), String.class);
            try {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = mapper.readTree(response.getBody());
                JsonNode dataArray = root.path("response").path("body").path("items").path("item");

                for (JsonNode data : dataArray) {
                    String city = data.path("ctpvNm").asText();

                    if (!city.equals("서울특별시")) {
                        continue;
                    }

                    String address = String.format("%s %s %s", city, data.path("sggNm").asText(), data.path("roadNm").asText());
                    int roadNumZip = data.path("roadNmZip").asInt();

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

                        SexOffenderDTO dto = SexOffenderDTO.builder()
                                .address(address)
                                .roadZip(roadNumZip)
                                .latitude(latitude)
                                .longitude(longitude)
                                .build();
                        sexOffenderList.add(SexOffender.of(dto));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }

        sexOffenderRepository.saveAll(sexOffenderList);
    }
}
