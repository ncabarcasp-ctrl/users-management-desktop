package com.jcaa.usersmanagement.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.jcaa.usersmanagement.application.port.out.GetRadioByIdPort;
import com.jcaa.usersmanagement.application.port.out.UpdateRadioPort;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateRadioCommand;
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
 * Tests para UpdateRadioService.
 *
 * <p>Cubre: flujo feliz (el id no cambia), radio no encontrada y validacion del command.
 */
@DisplayName("UpdateRadioService")
@ExtendWith(MockitoExtension.class)
class UpdateRadioServiceTest {

  @Mock private UpdateRadioPort updateRadioPort;
  @Mock private GetRadioByIdPort getRadioByIdPort;

  private UpdateRadioService service;

  @BeforeEach
  void setUp() {
    try (final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
      service =
          new UpdateRadioService(updateRadioPort, getRadioByIdPort, validatorFactory.getValidator());
    }
  }

  @Test
  @DisplayName("execute() actualiza los datos y conserva el id")
  void shouldUpdateWhenRadioExists() {
    // Arrange
    final Radio existing =
        new Radio(
            new RadioId("r-001"),
            new NombreRadio("Tropicana"),
            new Frecuencia(98.5),
            TipoTransmision.FM);
    final UpdateRadioCommand command = new UpdateRadioCommand("r-001", "Nueva", "1010", "AM");
    when(getRadioByIdPort.getById(any())).thenReturn(Optional.of(existing));
    when(updateRadioPort.update(any())).thenAnswer(invocation -> invocation.getArgument(0));

    // Act
    final Radio result = service.execute(command);

    // Assert
    assertEquals(new RadioId("r-001"), result.id());
    assertEquals("Nueva", result.nombre().valor());
    assertEquals(1010, result.frecuencia().valor());
    assertEquals(TipoTransmision.AM, result.tipoTransmision());
    verify(updateRadioPort).update(existing);
  }

  @Test
  @DisplayName("execute() lanza RadioNoEncontradoException cuando el id no existe")
  void shouldThrowWhenRadioNotFound() {
    // Arrange
    final UpdateRadioCommand command = new UpdateRadioCommand("no-existe", "Nueva", "1010", "AM");
    when(getRadioByIdPort.getById(any())).thenReturn(Optional.empty());

    // Act & Assert
    assertThrows(RadioNoEncontradoException.class, () -> service.execute(command));
    verify(updateRadioPort, never()).update(any());
  }

  @Test
  @DisplayName("execute() lanza ConstraintViolationException cuando la frecuencia esta en blanco")
  void shouldThrowWhenCommandIsInvalid() {
    // Arrange
    final UpdateRadioCommand command = new UpdateRadioCommand("r-001", "Nueva", " ", "AM");

    // Act & Assert
    assertThrows(ConstraintViolationException.class, () -> service.execute(command));
    verifyNoInteractions(updateRadioPort, getRadioByIdPort);
  }
}
