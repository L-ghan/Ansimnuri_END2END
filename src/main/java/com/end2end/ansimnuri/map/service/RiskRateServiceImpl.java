package com.end2end.ansimnuri.map.service;

import com.end2end.ansimnuri.map.dao.RiskRateDAO;
import com.end2end.ansimnuri.map.domain.entity.RiskRate;
import com.end2end.ansimnuri.map.domain.repository.CctvRepository;
import com.end2end.ansimnuri.map.domain.repository.PoliceRepository;
import com.end2end.ansimnuri.map.domain.repository.RiskRateRepository;
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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wololo.geojson.FeatureCollection;
import org.wololo.geojson.GeoJSON;
import org.wololo.jts2geojson.GeoJSONReader;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RiskRateServiceImpl implements RiskRateService {
    private final RiskRateDAO riskRateDAO;
    private final RiskRateRepository riskRateRepository;
    private final CctvRepository cctvRepository;
    private final PoliceRepository policeRepository;

    private Geometry seoulPoly;
    private final GeometryFactory geometryFactory = new GeometryFactory();

    @Value("${nasa.earthdata.user}")
    private String earthDataUser;
    @Value("${nasa.earthdata.pass}")
    private String earthDataPass;

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

                RiskRate riskRate = RiskRate.builder()
                        .latitude(p.getX())
                        .longitude(p.getY())
                        .illuminance(0.0)
                        .riskRate(0)
                        .build();
                batch.add(riskRate);

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

    @Override
    public int getRiskRate(double latitude, double longitude) {
        GeoUtil.Rectangle cctvPoints = new GeoUtil.Rectangle(0.2, latitude, longitude);

        List<CctvDTO> cctvList = cctvRepository.findByLatitudeBetweenAndLongitudeBetween(
                cctvPoints.getSouth(), cctvPoints.getNorth(), cctvPoints.getWest(), cctvPoints.getEast()).stream()
                .map(CctvDTO::of)
                .toList();

        PoliceDTO nearPolice = policeRepository.findAll().stream()
                .min(Comparator.comparingDouble(police ->
                        GeoUtil.getDistance(latitude, longitude, police.getLatitude(), police.getLongitude())))
                .map(PoliceDTO::of)
                .orElse(null);

        return 0;
    }
}
