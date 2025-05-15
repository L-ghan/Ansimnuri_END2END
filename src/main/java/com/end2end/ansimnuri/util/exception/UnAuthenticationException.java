package com.end2end.ansimnuri.util.exception;

public class UnAuthenticationException extends RuntimeException {
    public UnAuthenticationException(String message) {
        super(message);
    }
    public UnAuthenticationException(Exception e) {
        super("해당 유저는 접근 권한이 없습니다.", e);
    }
    public UnAuthenticationException() {
        super("해당 유저는 접근 권한이 없습니다.");
    }
}
