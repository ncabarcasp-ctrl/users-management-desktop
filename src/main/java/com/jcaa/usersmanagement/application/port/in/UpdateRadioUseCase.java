package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.application.service.dto.command.UpdateRadioCommand;
import com.jcaa.usersmanagement.domain.model.Radio;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface UpdateRadioUseCase {
  Radio execute(@NotNull @Valid UpdateRadioCommand command);
}
