package com.jcaa.usersmanagement.infrastructure.adapter.persistence.mapper;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.jcaa.usersmanagement.domain.enums.TipoTransmision;
import com.jcaa.usersmanagement.domain.exception.RadioDatoInvalidoException;
import com.jcaa.usersmanagement.domain.model.Radio;
import com.jcaa.usersmanagement.domain.valueobject.Frecuencia;
import com.jcaa.usersmanagement.domain.valueobject.NombreRadio;
import com.jcaa.usersmanagement.domain.valueobject.RadioId;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.entity.RadioEntity;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@DisplayName("RadioPersistenceMapper")
@ExtendWith(MockitoExtension.class)
class RadioPersistenceMapperTest {

  @Mock private ResultSet resultSet;

  private void configureRow() throws SQLException {
    when(resultSet.getString("id")).thenReturn("r-001");
    when(resultSet.getString("name")).thenReturn("Tropicana");
    when(resultSet.getDouble("frequency")).thenReturn(98.5);
    when(resultSet.getString("transmission_type")).thenReturn("FM");
  }

  @Test
  @DisplayName("fromModelToEntity() copies every field of the radio")
  void shouldMapModelToEntity() {
    final Radio radio =
        new Radio(
            new RadioId("r-001"), new NombreRadio("Tropicana"), new Frecuencia(98.5), TipoTransmision.FM);

    final RadioEntity entity = RadioPersistenceMapper.fromModelToEntity(radio);

    assertEquals(new RadioEntity("r-001", "Tropicana", 98.5, "FM"), entity);
  }

  @Test
  @DisplayName("fromEntityToModel() rebuilds the radio with its value objects")
  void shouldMapEntityToModel() {
    final Radio radio =
        RadioPersistenceMapper.fromEntityToModel(new RadioEntity("r-001", "Caracol", 1010, "AM"));

    assertEquals("Caracol", radio.nombre().valor());
    assertEquals(1010, radio.frecuencia().valor());
    assertEquals(TipoTransmision.AM, radio.tipoTransmision());
  }

  @Test
  @DisplayName("fromEntityToModel() rejects an invalid transmission type")
  void shouldRejectInvalidTypeFromEntity() {
    final RadioEntity entity = new RadioEntity("r-001", "Caracol", 1010, "XX");

    assertThrows(RadioDatoInvalidoException.class, () -> RadioPersistenceMapper.fromEntityToModel(entity));
  }

  @Test
  @DisplayName("fromResultSetToModel() reads the current row")
  void shouldMapResultSetToModel() throws SQLException {
    configureRow();

    final Radio radio = RadioPersistenceMapper.fromResultSetToModel(resultSet);

    assertEquals("r-001", radio.id().valor());
    assertEquals(98.5, radio.frecuencia().valor());
  }

  @Test
  @DisplayName("fromResultSetToModelList() reads every row until the cursor ends")
  void shouldMapResultSetToList() throws SQLException {
    configureRow();
    when(resultSet.next()).thenReturn(true, true, false);

    final List<Radio> radios = RadioPersistenceMapper.fromResultSetToModelList(resultSet);

    assertEquals(2, radios.size());
  }
}
