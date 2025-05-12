package com.end2end.ansimnuri.util.config;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.websocket.HandshakeResponse;
import jakarta.websocket.server.HandshakeRequest;
import jakarta.websocket.server.ServerEndpointConfig;

public class WebSocketConfig extends ServerEndpointConfig.Configurator {
    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        if (request instanceof ServletRequest) {
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String loginId = (String) httpRequest.getAttribute("loginId");

            if(loginId != null) {
                sec.getUserProperties().put("loginId", loginId);
            }
        }
    }
}