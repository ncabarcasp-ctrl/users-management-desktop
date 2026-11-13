package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto;

public record UpdateRadioRequest(
    String id,
    String name,
    String frequency,
    String transmissionType) {}
