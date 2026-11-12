package com.jcaa.usersmanagement.domain.valueobject;

import com.jcaa.usersmanagement.domain.exception.RadioDatoInvalidoException;
import java.util.Objects;
import java.util.UUID;

public record RadioId(String valor) {

  public RadioId {
    if (Objects.isNull(valor) || valor.isBlank()) {
      throw RadioDatoInvalidoException.becauseIdIsEmpty();
    }
    valor = valor.trim();
  }

  public static RadioId generar() {
    return new RadioId(UUID.randomUUID().toString());
  }

  @Override
  public String toString() {
    return valor;
  }
}
