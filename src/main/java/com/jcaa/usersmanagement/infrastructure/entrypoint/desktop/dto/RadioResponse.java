package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto;

public record RadioResponse(
    String id,
    String name,
    String frequency,
    String transmissionType) {}
