package com.jcaa.usersmanagement.application.service.dto.command;

import jakarta.validation.constraints.NotBlank;

public record UpdateRadioCommand(
    @NotBlank(message = "id must not be blank") String id,
    @NotBlank(message = "name must not be blank") String name,
    @NotBlank(message = "frequency must not be blank") String frequency,
    @NotBlank(message = "transmission type must not be blank") String transmissionType) {

}
