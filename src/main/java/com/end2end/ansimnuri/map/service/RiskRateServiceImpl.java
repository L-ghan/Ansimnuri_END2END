package com.end2end.ansimnuri.map.service;

import com.end2end.ansimnuri.map.dao.RiskRateDAO;
import com.end2end.ansimnuri.map.domain.entity.*;
import com.end2end.ansimnuri.map.domain.repository.*;
import com.end2end.ansimnuri.util.GeoUtil;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.wololo.geojson.FeatureCollection;
import org.wololo.jts2geojson.GeoJSONReader;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
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
    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public void insertAll() {
        riskRateRepository.deleteAll();

        Envelope env = seoulPoly.getEnvelopeInternal();
        double minLon = env.getMinX(), maxLon = env.getMaxX();
        double minLat = env.getMinY(), maxLat = env.getMaxY();

        double dLon = 200.0 / 111320.0;
        double dLat = 200.0 / 110574.0;

        List<RiskRate> batch = new ArrayList<>();
        final int BATCH_SIZE = 100;

        List<Police> policeList = policeRepository.findAllByOrderByLatitudeAscLongitudeAsc();
        List<SexOffender> sexOffenderList = sexOffenderRepository.findAllByOrderByLatitudeAscLongitudeAsc();
        List<StreetLight> streetLightList = streetLightRepository.findAllByOrderByLatitudeAscLongitudeAsc();

        int currentPoliceIndex = 0;
        int currentSexOffenderIndex = 0;
        int currentStreetLightIndex = 0;

        for (double lon = minLon; lon <= maxLon; lon += dLon) {
            for (double lat = minLat; lat <= maxLat; lat += dLat) {
                Point p = geometryFactory.createPoint(new Coordinate(lon, lat));
                if (!seoulPoly.contains(p)) {
                    continue;
                }

                Police nearPolice = null;
                if (currentPoliceIndex < policeList.size() - 1) {
                    Police currentIndexPolice = policeList.get(currentPoliceIndex);
                    Police nextIndexPolice = policeList.get(currentPoliceIndex + 1);

                    double currentPoliceDistance =
                            GeoUtil.getDistance(currentIndexPolice.getLatitude(), currentIndexPolice.getLongitude(), p.getX(), p.getY());
                    double nextPoliceDistance =
                            GeoUtil.getDistance(nextIndexPolice.getLatitude(), nextIndexPolice.getLongitude(), p.getX(), p.getY());

                    double result = Math.min(currentPoliceDistance, nextPoliceDistance);
                    nearPolice = (result == currentPoliceDistance) ? currentIndexPolice : nextIndexPolice;

                    if (result == nextPoliceDistance) {
                        currentPoliceIndex++;
                    }
                } else {
                    nearPolice = policeList.get(policeList.size() - 1);
                }

                StreetLight nearStreetLight = null;
                if (currentStreetLightIndex < streetLightList.size() - 1) {
                    StreetLight currentIndexStreetLight = streetLightList.get(currentStreetLightIndex);
                    StreetLight nextIndexStreetLight = streetLightList.get(currentStreetLightIndex + 1);

                    double currentStreetLightDistance =
                            GeoUtil.getDistance(currentIndexStreetLight.getLatitude(), currentIndexStreetLight.getLongitude(), p.getX(), p.getY());
                    double nextStreetLightDistance =
                            GeoUtil.getDistance(nextIndexStreetLight.getLatitude(), nextIndexStreetLight.getLongitude(), p.getX(), p.getY());

                    double result = Math.min(currentStreetLightDistance, nextStreetLightDistance);

                    nearStreetLight = (result == currentStreetLightDistance) ? currentIndexStreetLight : nextIndexStreetLight;
                    if (result == nextStreetLightDistance) {
                        currentStreetLightIndex++;
                    }
                } else {
                    nearStreetLight = streetLightList.get(streetLightList.size() - 1);
                }

                SexOffender nearSexOffender = null;
                if (currentSexOffenderIndex < sexOffenderList.size() - 1) {
                    SexOffender currentSexOffender = sexOffenderList.get(currentSexOffenderIndex);
                    SexOffender nextSexOffender = sexOffenderList.get(currentSexOffenderIndex + 1);

                    double currentDistance =
                            GeoUtil.getDistance(currentSexOffender.getLatitude(), currentSexOffender.getLongitude(), p.getX(), p.getY());
                    double nextDistance =
                            GeoUtil.getDistance(nextSexOffender.getLatitude(), nextSexOffender.getLongitude(), p.getX(), p.getY());

                    double result = Math.min(currentDistance, nextDistance);

                    nearSexOffender = (result == currentDistance) ? currentSexOffender : nextSexOffender;
                    if (result != currentDistance) {
                        currentSexOffenderIndex++;
                    }
                } else {
                    nearSexOffender = sexOffenderList.get(currentSexOffenderIndex);
                }

                RiskRate riskRate = RiskRate.builder()
                        .latitude(p.getX())
                        .longitude(p.getY())
                        .police(nearPolice)
                        .streetLight(nearStreetLight)
                        .sexOffender(nearSexOffender)
                        .build();

                GeoUtil.Rectangle cctvPoints =
                        new GeoUtil.Rectangle(0.2, riskRate.getLatitude(), riskRate.getLongitude());
                List<Cctv> cctvList = cctvRepository.findByLatitudeBetweenAndLongitudeBetween(
                        cctvPoints.getSouth(), cctvPoints.getNorth(), cctvPoints.getWest(), cctvPoints.getEast());
                cctvList.forEach(cctv -> {
                            cctv.update(riskRate);
                        });

                riskRate.setRiskRate(getRiskRate(riskRate, cctvList));
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

    private int getRiskRate(RiskRate riskRate, List<Cctv> cctvList) {
        int cctvCount = cctvList.size();
        Police police = riskRate.getPolice();
        StreetLight streetLight = riskRate.getStreetLight();
        SexOffender sexOffender = riskRate.getSexOffender();

        double policeDistance =
                GeoUtil.getDistance(police.getLatitude(), police.getLongitude(), riskRate.getLatitude(), riskRate.getLongitude());
        double streetLightDistance =
                GeoUtil.getDistance(streetLight.getLatitude(), streetLight.getLongitude(), riskRate.getLatitude(), riskRate.getLongitude());
        double sexOffenderDistance =
                GeoUtil.getDistance(sexOffender.getLatitude(), sexOffender.getLongitude(), riskRate.getLatitude(), riskRate.getLongitude());

        double cctvScore = 1.0 - Math.min(cctvCount / 10.0, 1.0);
        double policeScore = Math.min(policeDistance / 1000.0, 1.0);
        double lightScore = Math.min(streetLightDistance / 500.0, 1.0);
        double offenderScore = 1.0 - Math.min(sexOffenderDistance / 500.0, 1.0);

        double totalScore = cctvScore * 0.25 +
                policeScore * 0.25 +
                lightScore * 0.2 +
                offenderScore * 0.3;

        if (totalScore >= 0.8) return 5;         // 매우 위험
        else if (totalScore >= 0.6) return 4;    // 위험
        else if (totalScore >= 0.4) return 3;    // 보통
        else if (totalScore >= 0.2) return 2;    // 안전
        else return 1;                           // 매우 안전
    }
}
