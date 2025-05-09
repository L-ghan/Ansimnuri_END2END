package com.end2end.ansimnuri.util.enums;

public enum Roles {
    USER("ROLE_USER"), ADMIN("ROLE_ADMIN");

    private String role;

    private Roles(String role) {
        this.role = role;
    }
}
