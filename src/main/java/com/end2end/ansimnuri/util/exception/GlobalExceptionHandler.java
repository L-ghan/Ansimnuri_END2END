package com.end2end.ansimnuri.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UnAuthenticationException.class)
    public ResponseEntity<String> handleUnAuthenticationException(UnAuthenticationException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    @ExceptionHandler(BanUserException.class)
    public ResponseEntity<String> handleBanUserException(BanUserException e) {
        String message = String.format("%s님은 현재 차단 중입니다. %n차단 사유: %s    차단 기간: %s",
                e.getNickname(), e.getReason(), e.getEndDate().toString());

        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(message);
    }

    @ExceptionHandler(ApiConnectException.class)
    public ResponseEntity<String> handleApiConnectException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("서버에 에러가 발생했습니다.");
    }
}
