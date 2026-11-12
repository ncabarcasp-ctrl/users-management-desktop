package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.DeleteRadioUseCase;
import com.jcaa.usersmanagement.application.port.out.DeleteRadioPort;
import com.jcaa.usersmanagement.application.port.out.GetRadioByIdPort;
import com.jcaa.usersmanagement.application.service.dto.command.DeleteRadioCommand;
import com.jcaa.usersmanagement.application.service.mapper.RadioApplicationMapper;
import com.jcaa.usersmanagement.domain.exception.RadioNoEncontradoException;
import com.jcaa.usersmanagement.domain.valueobject.RadioId;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@RequiredArgsConstructor
public final class DeleteRadioService implements DeleteRadioUseCase {

  private final DeleteRadioPort deleteRadioPort;
  private final GetRadioByIdPort getRadioByIdPort;
  private final Validator validator;

  @Override
  public void execute(final DeleteRadioCommand command) {
    validateCommand(command);

    final RadioId radioId = RadioApplicationMapper.fromDeleteCommandToRadioId(command);
    ensureRadioExists(radioId);
    deleteRadioPort.delete(radioId);
  }

  private void validateCommand(final DeleteRadioCommand command) {
    final Set<ConstraintViolation<DeleteRadioCommand>> violations = validator.validate(command);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }

  private void ensureRadioExists(final RadioId radioId) {
    getRadioByIdPort
        .getById(radioId)
        .orElseThrow(() -> RadioNoEncontradoException.becauseIdWasNotFound(radioId.valor()));
  }
}
