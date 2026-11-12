package com.jcaa.usersmanagement.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.jcaa.usersmanagement.application.port.out.SaveRadioPort;
import com.jcaa.usersmanagement.application.service.dto.command.CreateRadioCommand;
import com.jcaa.usersmanagement.domain.enums.TipoTransmision;
import com.jcaa.usersmanagement.domain.exception.RadioDatoInvalidoException;
import com.jcaa.usersmanagement.domain.model.Radio;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Tests para CreateRadioService.
 *
 * <p>Cubre: flujo feliz (id generado), validacion del command y valores de dominio invalidos.
 */
@DisplayName("CreateRadioService")
@ExtendWith(MockitoExtension.class)
class CreateRadioServiceTest {

  @Mock private SaveRadioPort saveRadioPort;

  private CreateRadioService service;

  @BeforeEach
  void setUp() {
    try (final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory()) {
      service = new CreateRadioService(saveRadioPort, validatorFactory.getValidator());
    }
  }

  @Test
  @DisplayName("execute() guarda la radio y le genera un id")
  void shouldSaveRadioWithGeneratedId() {
    // Arrange
    final CreateRadioCommand command = new CreateRadioCommand("Tropicana", "98.5", "FM");
    when(saveRadioPort.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

    // Act
    final Radio result = service.execute(command);

    // Assert
    assertNotNull(result.id());
    assertEquals("Tropicana", result.nombre().valor());
    assertEquals(98.5, result.frecuencia().valor());
    assertEquals(TipoTransmision.FM, result.tipoTransmision());
    verify(saveRadioPort).save(any(Radio.class));
  }

  @Test
  @DisplayName("execute() lanza ConstraintViolationException cuando el nombre esta en blanco")
  void shouldThrowWhenCommandIsInvalid() {
    // Arrange
    final CreateRadioCommand command = new CreateRadioCommand(" ", "98.5", "FM");

    // Act & Assert
    assertThrows(ConstraintViolationException.class, () -> service.execute(command));
    verifyNoInteractions(saveRadioPort);
  }

  @Test
  @DisplayName("execute() lanza RadioDatoInvalidoException cuando la frecuencia no es numerica")
  void shouldThrowWhenFrequencyIsNotANumber() {
    // Arrange
    final CreateRadioCommand command = new CreateRadioCommand("Tropicana", "abc", "FM");

    // Act & Assert
    assertThrows(RadioDatoInvalidoException.class, () -> service.execute(command));
    verify(saveRadioPort, never()).save(any());
  }

  @Test
  @DisplayName("execute() lanza RadioDatoInvalidoException cuando el tipo no es AM ni FM")
  void shouldThrowWhenTransmissionTypeIsInvalid() {
    // Arrange
    final CreateRadioCommand command = new CreateRadioCommand("Tropicana", "98.5", "XX");

    // Act & Assert
    assertThrows(RadioDatoInvalidoException.class, () -> service.execute(command));
    verify(saveRadioPort, never()).save(any());
  }
}
