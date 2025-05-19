package com.end2end.ansimnuri.map.service;

public interface RiskRateService {
    void insertAll();
    int getRiskRate(double latitude, double longitude);
}
