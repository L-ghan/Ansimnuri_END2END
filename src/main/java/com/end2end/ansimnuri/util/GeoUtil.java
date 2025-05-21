package com.end2end.ansimnuri.util;

import lombok.Data;

public class GeoUtil {
    private static final double KM_PER_LATITUDE = 111.32;
    private static final int EARTH_RADIUS = 6371; // 지구의 반지름 (km)

    @Data
    public static class Rectangle {
        double north, south, east, west;

        public Rectangle(double distanceKm, double centerLat, double centerLng) {
            double latChange = distanceKm / KM_PER_LATITUDE;

            // 경도(longitude) 변화량 계산
            double lngChange = distanceKm / (KM_PER_LATITUDE * Math.cos(Math.toRadians(centerLat)));

            // 중심점에서 각각 200m 떨어진 좌표 계산
            north = centerLat + latChange;  // 위로 200m
            south = centerLat - latChange;  // 아래로 200m
            east = centerLng + lngChange;   // 오른쪽으로 200m
            west = centerLng - lngChange;   // 왼쪽으로 200m
        }
    }

    public static double getDistance(double lat1, double lng1, double lat2, double lng2) {
        double latDistance = Math.toRadians(lat2 - lat1);
        double lngDistance = Math.toRadians(lng2 - lng1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c; // 킬로미터 단위로 반환
    }
}
