package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.UpdateRadioUseCase;
import com.jcaa.usersmanagement.application.port.out.GetRadioByIdPort;
import com.jcaa.usersmanagement.application.port.out.UpdateRadioPort;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateRadioCommand;
import com.jcaa.usersmanagement.application.service.mapper.RadioApplicationMapper;
import com.jcaa.usersmanagement.domain.exception.RadioNoEncontradoException;
import com.jcaa.usersmanagement.domain.model.Radio;
import com.jcaa.usersmanagement.domain.valueobject.RadioId;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
public final class UpdateRadioService implements UpdateRadioUseCase {

  private final UpdateRadioPort updateRadioPort;
  private final GetRadioByIdPort getRadioByIdPort;
  private final Validator validator;

  @Override
  public Radio execute(final UpdateRadioCommand command) {
    validateCommand(command);

    final RadioId radioId = RadioApplicationMapper.fromUpdateCommandToRadioId(command);
    final Radio radio =
        getRadioByIdPort
            .getById(radioId)
            .orElseThrow(() -> RadioNoEncontradoException.becauseIdWasNotFound(radioId.valor()));

    RadioApplicationMapper.applyUpdateCommandToModel(radio, command);
    return updateRadioPort.update(radio);
  }

  private void validateCommand(final UpdateRadioCommand command) {
    final Set<ConstraintViolation<UpdateRadioCommand>> violations = validator.validate(command);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }
}
