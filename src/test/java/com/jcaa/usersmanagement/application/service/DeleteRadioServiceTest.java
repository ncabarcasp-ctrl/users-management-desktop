package com.jcaa.usersmanagement.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.jcaa.usersmanagement.application.port.out.DeleteRadioPort;
import com.jcaa.usersmanagement.application.port.out.GetRadioByIdPort;
import com.jcaa.usersmanagement.application.service.dto.command.DeleteRadioCommand;
import com.jcaa.usersmanagement.domain.enums.TipoTransmision;
import com.jcaa.usersmanagement.domain.exception.RadioNoEncontradoException;
import com.jcaa.usersmanagement.domain.model.Radio;
import com.jcaa.usersmanagement.domain.valueobject.Frecuencia;
import com.jcaa.usersmanagement.domain.valueobject.NombreRadio;
import com.jcaa.usersmanagement.domain.valueobject.RadioId;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Tests para DeleteRadioService.
 *
 * <p>Cubre: flujo feliz (delete invocado), radio no encontrada y validacion del command.
 */
@DisplayName("DeleteRadioService")
@ExtendWith(MockitoExtension.class)
class DeleteRadioServiceTest {

  @Mock private DeleteRadioPort deleteRadioPort;
  @Mock private GetRadioByIdPort getRadioByIdPort;

  private DeleteRadioService service;

  @BeforeEach
  void setUp() {
    try (final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
      service =
          new DeleteRadioService(deleteRadioPort, getRadioByIdPort, validatorFactory.getValidator());
    }
  }

  @Test
  @DisplayName("execute() invoca deleteRadioPort cuando la radio existe")
  void shouldDeleteWhenRadioExists() {
    // Arrange
    final Radio existing =
        new Radio(
            new RadioId("r-001"),
            new NombreRadio("Tropicana"),
            new Frecuencia(98.5),
            TipoTransmision.FM);
    when(getRadioByIdPort.getById(any())).thenReturn(Optional.of(existing));

    // Act
    service.execute(new DeleteRadioCommand("r-001"));

    // Assert
    verify(deleteRadioPort).delete(new RadioId("r-001"));
  }

  @Test
  @DisplayName("execute() lanza RadioNoEncontradoException cuando el id no existe")
  void shouldThrowWhenRadioNotFound() {
    // Arrange
    when(getRadioByIdPort.getById(any())).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(
        RadioNoEncontradoException.class, () -> service.execute(new DeleteRadioCommand("no-existe")));
    verify(deleteRadioPort, never()).delete(any());
  }

  @Test
  @DisplayName("execute() lanza ConstraintViolationException cuando el id esta en blanco")
  void shouldThrowWhenCommandIsInvalid() {
    // Act & Assert
    assertThrows(
        ConstraintViolationException.class, () -> service.execute(new DeleteRadioCommand("  ")));
    verifyNoInteractions(deleteRadioPort, getRadioByIdPort);
  }
}
