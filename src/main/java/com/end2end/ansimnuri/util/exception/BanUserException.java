package com.end2end.ansimnuri.util.exception;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class BanUserException extends RuntimeException {
    private final String nickname;
    private final String reason;
    private final LocalDateTime endDate;

    public BanUserException(String nickname, String reason,  LocalDateTime endDate) {
        super("해당 유저는 현재 차단중입니다.");
        this.nickname = nickname;
        this.reason = reason;
        this.endDate = endDate;
    }
}
