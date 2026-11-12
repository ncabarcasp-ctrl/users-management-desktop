package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.CreateRadioUseCase;
import com.jcaa.usersmanagement.application.port.out.SaveRadioPort;
import com.jcaa.usersmanagement.application.service.dto.command.CreateRadioCommand;
import com.jcaa.usersmanagement.application.service.mapper.RadioApplicationMapper;
import com.jcaa.usersmanagement.domain.model.Radio;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
public final class CreateRadioService implements CreateRadioUseCase {

  private final SaveRadioPort saveRadioPort;
  private final Validator validator;

  @Override
  public Radio execute(final CreateRadioCommand command) {
    validateCommand(command);

    final Radio radioToSave = RadioApplicationMapper.fromCreateCommandToModel(command);
    return saveRadioPort.save(radioToSave);
  }

  private void validateCommand(final CreateRadioCommand command) {
    final Set<ConstraintViolation<CreateRadioCommand>> violations = validator.validate(command);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }
}
