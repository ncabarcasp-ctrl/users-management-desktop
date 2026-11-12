package com.jcaa.usersmanagement.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.jcaa.usersmanagement.application.port.out.GetAllRadiosPort;
import com.jcaa.usersmanagement.domain.enums.TipoTransmision;
import com.jcaa.usersmanagement.domain.model.Radio;
import com.jcaa.usersmanagement.domain.valueobject.Frecuencia;
import com.jcaa.usersmanagement.domain.valueobject.NombreRadio;
import com.jcaa.usersmanagement.domain.valueobject.RadioId;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Tests para GetAllRadiosService.
 *
 * <p>Cubre: lista con elementos y lista vacia.
 */
@DisplayName("GetAllRadiosService")
@ExtendWith(MockitoExtension.class)
class GetAllRadiosServiceTest {

  @Mock private GetAllRadiosPort getAllRadiosPort;

  @InjectMocks private GetAllRadiosService service;

  @Test
  @DisplayName("execute() retorna todas las radios que entrega el puerto")
  void shouldReturnAllRadios() {
    // Arrange
    final List<Radio> radios =
        List.of(
            new Radio(
                new RadioId("r-001"),
                new NombreRadio("Tropicana"),
                new Frecuencia(98.5),
                TipoTransmision.FM),
            new Radio(
                new RadioId("r-002"),
                new NombreRadio("Caracol"),
                new Frecuencia(1010),
                TipoTransmision.AM));
    when(getAllRadiosPort.getAll()).thenReturn(radios);

    // Act & Assert
    assertEquals(radios, service.execute());
  }

  @Test
  @DisplayName("execute() retorna lista vacia cuando no hay radios")
  void shouldReturnEmptyListWhenThereAreNoRadios() {
    // Arrange
    when(getAllRadiosPort.getAll()).thenReturn(List.of());

    // Act & Assert
    assertTrue(service.execute().isEmpty());
  }
}
