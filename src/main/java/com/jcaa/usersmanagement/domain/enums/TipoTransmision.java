package com.jcaa.usersmanagement.domain.enums;

import com.jcaa.usersmanagement.domain.exception.RadioDatoInvalidoException;
import java.util.Objects;

public enum TipoTransmision {
  AM,
  FM;

  public static TipoTransmision desde(final String texto) {
    if (Objects.isNull(texto) || texto.isBlank()) {
      throw RadioDatoInvalidoException.becauseTransmissionTypeIsEmpty();
    }
    final String normalizado = texto.trim();
    for (final TipoTransmision tipo : values()) {
      if (tipo.name().equalsIgnoreCase(normalizado)) {
        return tipo;
      }
    }
    throw RadioDatoInvalidoException.becauseTransmissionTypeIsInvalid(texto);
  }
}
