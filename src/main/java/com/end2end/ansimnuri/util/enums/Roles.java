package com.end2end.ansimnuri.util.enums;

import lombok.Getter;

@Getter
public enum Roles {
    USER("ROLE_USER"), ADMIN("ROLE_ADMIN");

    private String role;

    private Roles(String role) {
        this.role = role;
    }
}
