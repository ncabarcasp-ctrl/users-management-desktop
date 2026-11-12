package com.jcaa.usersmanagement.domain.valueobject;

import com.jcaa.usersmanagement.domain.exception.RadioDatoInvalidoException;
import java.util.Locale;
import java.util.Objects;

public record NombreRadio(String valor) {

  private static final int MAXIMUM_LENGTH = 100;

  public NombreRadio {
    if (Objects.isNull(valor) || valor.isBlank()) {
      throw RadioDatoInvalidoException.becauseNameIsEmpty();
    }
    valor = valor.trim();
    if (valor.length() > MAXIMUM_LENGTH) {
      throw RadioDatoInvalidoException.becauseNameIsTooLong(MAXIMUM_LENGTH);
    }
  }

  /** Two names are equal when they match ignoring upper/lower case. */
  @Override
  public boolean equals(final Object other) {
    return other instanceof NombreRadio nombre && valor.equalsIgnoreCase(nombre.valor);
  }

  @Override
  public int hashCode() {
    return valor.toLowerCase(Locale.ROOT).hashCode();
  }

  @Override
  public String toString() {
    return valor;
  }
}
