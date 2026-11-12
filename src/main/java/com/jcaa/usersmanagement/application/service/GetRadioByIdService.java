package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.GetRadioByIdUseCase;
import com.jcaa.usersmanagement.application.port.out.GetRadioByIdPort;
import com.jcaa.usersmanagement.application.service.dto.query.GetRadioByIdQuery;
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
public final class GetRadioByIdService implements GetRadioByIdUseCase {

  private final GetRadioByIdPort getRadioByIdPort;
  private final Validator validator;

  @Override
  public Radio execute(final GetRadioByIdQuery query) {
    validateQuery(query);

    final RadioId radioId = RadioApplicationMapper.fromGetRadioByIdQueryToRadioId(query);
    return getRadioByIdPort
        .getById(radioId)
        .orElseThrow(() -> RadioNoEncontradoException.becauseIdWasNotFound(radioId.valor()));
  }

  private void validateQuery(final GetRadioByIdQuery query) {
    final Set<ConstraintViolation<GetRadioByIdQuery>> violations = validator.validate(query);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }
}
