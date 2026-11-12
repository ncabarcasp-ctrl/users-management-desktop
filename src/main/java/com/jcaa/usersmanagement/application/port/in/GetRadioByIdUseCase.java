package com.jcaa.usersmanagement.application.port.in;

import com.jcaa.usersmanagement.application.service.dto.query.GetRadioByIdQuery;
import com.jcaa.usersmanagement.domain.model.Radio;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public interface GetRadioByIdUseCase {
  Radio execute(@NotNull @Valid GetRadioByIdQuery query);
}
