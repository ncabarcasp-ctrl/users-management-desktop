package com.jcaa.usersmanagement.domain.model;

import com.jcaa.usersmanagement.domain.enums.TipoTransmision;
import com.jcaa.usersmanagement.domain.valueobject.Frecuencia;
import com.jcaa.usersmanagement.domain.valueobject.NombreRadio;
import com.jcaa.usersmanagement.domain.valueobject.RadioId;
import java.util.Objects;

/** Radio station entity. Its identity is the {@link RadioId}; the other fields can change. */
public final class Radio {

  private final RadioId id;
  private NombreRadio nombre;
  private Frecuencia frecuencia;
  private TipoTransmision tipoTransmision;

  public Radio(
      final RadioId id,
      final NombreRadio nombre,
      final Frecuencia frecuencia,
      final TipoTransmision tipoTransmision) {
    this.id = Objects.requireNonNull(id, "id cannot be null");
    this.nombre = Objects.requireNonNull(nombre, "nombre cannot be null");
    this.frecuencia = Objects.requireNonNull(frecuencia, "frecuencia cannot be null");
    this.tipoTransmision =
        Objects.requireNonNull(tipoTransmision, "tipoTransmision cannot be null");
  }

  public static Radio registrar(
      final NombreRadio nombre, final Frecuencia frecuencia, final TipoTransmision tipoTransmision) {
    return new Radio(RadioId.generar(), nombre, frecuencia, tipoTransmision);
  }

  public void actualizarDatos(
      final NombreRadio nombre, final Frecuencia frecuencia, final TipoTransmision tipoTransmision) {
    this.nombre = Objects.requireNonNull(nombre, "nombre cannot be null");
    this.frecuencia = Objects.requireNonNull(frecuencia, "frecuencia cannot be null");
    this.tipoTransmision =
        Objects.requireNonNull(tipoTransmision, "tipoTransmision cannot be null");
  }

  public RadioId id() {
    return id;
  }

  public NombreRadio nombre() {
    return nombre;
  }

  public Frecuencia frecuencia() {
    return frecuencia;
  }

  public TipoTransmision tipoTransmision() {
    return tipoTransmision;
  }

  @Override
  public boolean equals(final Object other) {
    return other instanceof Radio radio && id.equals(radio.id);
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }

  @Override
  public String toString() {
    return "Radio{id=" + id + ", nombre=" + nombre + ", frecuencia=" + frecuencia
        + ", tipoTransmision=" + tipoTransmision + "}";
  }
}
