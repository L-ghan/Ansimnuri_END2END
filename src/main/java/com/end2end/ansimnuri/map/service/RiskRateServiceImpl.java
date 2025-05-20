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
                        .build();
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
}
