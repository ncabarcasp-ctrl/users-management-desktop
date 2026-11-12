package com.jcaa.usersmanagement.domain.valueobject;

import com.jcaa.usersmanagement.domain.exception.RadioDatoInvalidoException;
import java.util.Objects;

public record Frecuencia(double valor) {

  public Frecuencia {
    if (!Double.isFinite(valor) || valor <= 0) {
      throw RadioDatoInvalidoException.becauseFrequencyIsNotPositive(valor);
    }
  }

  /** Parses text such as "98.5" or "98,5". */
  public static Frecuencia desde(final String texto) {
    if (Objects.isNull(texto) || texto.isBlank()) {
      throw RadioDatoInvalidoException.becauseFrequencyIsNotANumber(texto);
    }
    try {
      return new Frecuencia(Double.parseDouble(texto.trim().replace(',', '.')));
    } catch (final NumberFormatException exception) {
      throw RadioDatoInvalidoException.becauseFrequencyIsNotANumber(texto);
    }
  }

  @Override
  public String toString() {
    return String.valueOf(valor);
  }
}
