package com.end2end.ansimnuri.util.exception;

public class ApiConnectException extends RuntimeException {
    public ApiConnectException(String message) {
        super(message);
    }
    public ApiConnectException(Exception e) {
        super("api와의 연결이 실패했습니다.");
    }
}
