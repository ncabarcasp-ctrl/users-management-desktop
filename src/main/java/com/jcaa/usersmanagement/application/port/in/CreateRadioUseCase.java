package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.application.service.dto.command.CreateRadioCommand;
import com.jcaa.usersmanagement.domain.model.Radio;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface CreateRadioUseCase {
  Radio execute(@NotNull @Valid CreateRadioCommand command);
}
