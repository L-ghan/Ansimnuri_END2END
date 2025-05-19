package com.end2end.ansimnuri.map.service;

import com.end2end.ansimnuri.map.dao.RiskRateDAO;
import com.end2end.ansimnuri.map.domain.entity.Police;
import com.end2end.ansimnuri.map.domain.entity.RiskRate;
import com.end2end.ansimnuri.map.domain.entity.SexOffender;
import com.end2end.ansimnuri.map.domain.entity.StreetLight;
import com.end2end.ansimnuri.map.domain.repository.*;
import com.end2end.ansimnuri.map.dto.CctvDTO;
import com.end2end.ansimnuri.map.dto.PoliceDTO;
import com.end2end.ansimnuri.util.GeoUtil;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.wololo.geojson.FeatureCollection;
import org.wololo.jts2geojson.GeoJSONReader;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.*;

@RequiredArgsConstructor
@Service
public class RiskRateServiceImpl implements RiskRateService {
    private final RiskRateDAO riskRateDAO;
    private final RiskRateRepository riskRateRepository;
    private final CctvRepository cctvRepository;
    private final PoliceRepository policeRepository;
    private final StreetLightRepository streetLightRepository;
    private final SexOffenderRepository sexOffenderRepository;

    private Geometry seoulPoly;
    private final GeometryFactory geometryFactory = new GeometryFactory();

    @Value("${nasa.earthdata.user}")
    private String earthDataUser;
    @Value("${nasa.earthdata.pass}")
    private String earthDataPass;
    @Value("${nasa.earthdata.token}")
    private String earthDataToken;

    @PostConstruct
    public void loadSeoulBoundary() throws Exception {
        ClassPathResource res = new ClassPathResource("/static/geojson/seoul.geojson");
        try (InputStream in = res.getInputStream()) {
            String geoJson = new String(in.readAllBytes(), StandardCharsets.UTF_8);

            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            FeatureCollection featureCollection =
                    mapper.readValue(geoJson, FeatureCollection.class);

            if (featureCollection.getFeatures().length > 0) {
                GeoJSONReader reader = new GeoJSONReader();
                this.seoulPoly = reader.read(featureCollection.getFeatures()[0].getGeometry());
            } else {
                throw new IllegalStateException("서울 경계 데이터를 찾을 수 없습니다.");
            }
        }
    }

    @Scheduled(cron = "0 0 0 1 1 *")
    @Transactional
    @Override
    public void insertAll() {
        riskRateRepository.deleteAll();

        Envelope env = seoulPoly.getEnvelopeInternal();
        double minLon = env.getMinX(), maxLon = env.getMaxX();
        double minLat = env.getMinY(), maxLat = env.getMaxY();

        double dLon = 50.0 / 111320.0;
        double dLat = 50.0 / 110574.0;

        List<RiskRate> batch = new ArrayList<>();
        final int BATCH_SIZE = 500;

        for (double lon = minLon; lon <= maxLon; lon += dLon) {
            for (double lat = minLat; lat <= maxLat; lat += dLat) {
                Point p = geometryFactory.createPoint(new Coordinate(lon, lat));
                if (!seoulPoly.contains(p)) {
                    continue;
                }

                Police nearPolice = policeRepository.findAll().stream()
                        .min(Comparator.comparingDouble(police ->
                                GeoUtil.getDistance(
                                        p.getX(), p.getY(), police.getLatitude(), police.getLongitude())))
                        .orElse(null);
                StreetLight nearStreetLight = streetLightRepository.findAll().stream()
                        .min(Comparator.comparingDouble(streetLight ->
                                GeoUtil.getDistance(p.getX(), p.getY(), streetLight.getLatitude(), streetLight.getLongitude())))
                        .orElse(null);
                SexOffender nearSexOffender = sexOffenderRepository.findAll().stream()
                        .min(Comparator.comparingDouble(sexOffender ->
                            GeoUtil.getDistance(p.getX(), p.getY(), sexOffender.getLatitude(), sexOffender.getLongitude())))
                        .orElse(null);

                RiskRate riskRate = RiskRate.builder()
                        .latitude(p.getX())
                        .longitude(p.getY())
                        .riskRate(0)
                        .police(nearPolice)
                        .streetLight(nearStreetLight)
                        .sexOffender(nearSexOffender)
                        .illuminance(0.0)
                        .build();
                //riskRate.setIlluminance(getIlluminance(riskRate));
                batch.add(riskRate);

                GeoUtil.Rectangle cctvPoints =
                        new GeoUtil.Rectangle(0.2, riskRate.getLatitude(), riskRate.getLongitude());
                cctvRepository.findByLatitudeBetweenAndLongitudeBetween(
                        cctvPoints.getSouth(), cctvPoints.getNorth(), cctvPoints.getWest(), cctvPoints.getEast())
                        .forEach(cctv -> {
                            cctv.update(riskRate);
                        });

                if (batch.size() >= BATCH_SIZE) {
                    riskRateRepository.saveAll(batch);
                    riskRateRepository.flush();
                    batch.clear();
                }
            }
        }

        if (!batch.isEmpty()) {
            riskRateRepository.saveAll(batch);
            riskRateRepository.flush();
        }
    }

    private int getRiskRate(RiskRate riskRate) {
        return 0;
    }

    private double getIlluminance(RiskRate riskRate) {
        double latitude = riskRate.getLatitude();
        double longitude = riskRate.getLongitude();

        String earthDataURL = "https://appeears.earthdatacloud.nasa.gov/api/";
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(earthDataToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        // 요청 본문 구성
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("task_type", "point");
        requestBody.put("task_name", "NightLight_" + UUID.randomUUID());

        // 좌표 정보
        Map<String, Object> coordinates = new HashMap<>();
        coordinates.put("coordinates", Arrays.asList(longitude, latitude));
        coordinates.put("type", "Point");

        // 데이터 레이어 정보
        Map<String, Object> layer = new HashMap<>();
        layer.put("product", "VNP46A2");
        layer.put("layer", "DNB_BRDF_Corrected_NTL");

        requestBody.put("geometry", coordinates);
        requestBody.put("layers", Collections.singletonList(layer));
        requestBody.put("dates", Collections.singletonList(LocalDate.now().toString()));

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

        try {
            // 작업 생성 요청
            ResponseEntity<Map> taskResponse = restTemplate.exchange(
                    earthDataURL + "task",
                    HttpMethod.POST,
                    entity,
                    Map.class
            );

            if (taskResponse.getStatusCode() == HttpStatus.CREATED && taskResponse.getBody() != null) {
                String taskId = (String) taskResponse.getBody().get("task_id");

                // 작업 완료 대기
                String taskStatus;
                do {
                    ResponseEntity<Map> statusResponse = restTemplate.exchange(
                            earthDataURL + "task/" + taskId,
                            HttpMethod.GET,
                            new HttpEntity<>(headers),
                            Map.class
                    );

                    taskStatus = (String) statusResponse.getBody().get("status");
                    if ("failed".equals(taskStatus)) {
                        throw new RuntimeException("작업 실패");
                    }

                    Thread.sleep(5000); // 5초 대기
                } while ("running".equals(taskStatus) || "pending".equals(taskStatus));

                // 결과 데이터 가져오기
                ResponseEntity<Map> resultResponse = restTemplate.exchange(
                        earthDataURL + "task/" + taskId + "/bundle",
                        HttpMethod.GET,
                        new HttpEntity<>(headers),
                        Map.class
                );

                if (resultResponse.getBody() != null && resultResponse.getBody().containsKey("data")) {
                    @SuppressWarnings("unchecked")
                    List<Map<String, Object>> data = (List<Map<String, Object>>) resultResponse.getBody().get("data");
                    if (!data.isEmpty()) {
                        return Double.parseDouble(data.get(0).get("value").toString());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 기본값 또는 오류 시 반환값
        return 0.0;
    }
}
