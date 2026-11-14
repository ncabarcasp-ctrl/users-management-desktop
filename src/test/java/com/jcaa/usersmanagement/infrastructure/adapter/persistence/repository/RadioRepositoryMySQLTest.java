package com.jcaa.usersmanagement.infrastructure.adapter.persistence.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import com.jcaa.usersmanagement.domain.enums.TipoTransmision;
import com.jcaa.usersmanagement.domain.exception.RadioNoEncontradoException;
import com.jcaa.usersmanagement.domain.model.Radio;
import com.jcaa.usersmanagement.domain.valueobject.Frecuencia;
import com.jcaa.usersmanagement.domain.valueobject.NombreRadio;
import com.jcaa.usersmanagement.domain.valueobject.RadioId;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.exception.PersistenceException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Tests for RadioRepositoryMySQL.
 *
 * <p>Covers: save() happy path, INSERT failure, radio not found after insert; update() happy path,
 * UPDATE failure; getById() found, not found, SQLException; getAll() happy path, SQLException;
 * delete() happy path, SQLException.
 */
@DisplayName("RadioRepositoryMySQL")
@ExtendWith(MockitoExtension.class)
class RadioRepositoryMySQLTest {

  private static final String ID = "r-001";
  private static final String NAME = "Tropicana";
  private static final double FREQUENCY = 98.5;
  private static final String TYPE = "FM";

  @Mock private Connection connection;
  @Mock private PreparedStatement statement;
  @Mock private ResultSet resultSet;

  private RadioRepositoryMySQL repository;
  private Radio radio;
  private RadioId radioId;

  @BeforeEach
  void setUp() {
    repository = new RadioRepositoryMySQL(connection);
    radioId = new RadioId(ID);
    radio = new Radio(radioId, new NombreRadio(NAME), new Frecuencia(FREQUENCY), TipoTransmision.FM);
  }

  // Helper: wire connection -> statement -> resultSet
  private void configureStatementAndResultSet() throws SQLException {
    when(connection.prepareStatement(anyString())).thenReturn(statement);
    when(statement.executeQuery()).thenReturn(resultSet);
  }

  // Helper: configure resultSet to return one full radio row
  private void configureResultSetRow() throws SQLException {
    when(resultSet.getString("id")).thenReturn(ID);
    when(resultSet.getString("name")).thenReturn(NAME);
    when(resultSet.getDouble("frequency")).thenReturn(FREQUENCY);
    when(resultSet.getString("transmission_type")).thenReturn(TYPE);
  }

  // -- save()

  @Test
  @DisplayName("save() executes INSERT and returns the persisted radio fetched by id")
  void shouldSaveRadioAndReturnById() throws SQLException {
    // Arrange
    configureStatementAndResultSet();
    when(resultSet.next()).thenReturn(true);
    configureResultSetRow();

    // Act
    final Radio result = repository.save(radio);

    // Assert
    assertAll(
        "save() happy path",
        () -> assertEquals(ID, result.id().valor(), "id"),
        () -> assertEquals(NAME, result.nombre().valor(), "name"),
        () -> assertEquals(FREQUENCY, result.frecuencia().valor(), "frequency"),
        () -> assertEquals(TipoTransmision.FM, result.tipoTransmision(), "type"));
    verify(statement, atLeastOnce()).setString(1, ID);
    verify(statement).setString(2, NAME);
    verify(statement).setDouble(3, FREQUENCY);
    verify(statement).setString(4, TYPE);
  }

  @Test
  @DisplayName("save() throws PersistenceException when INSERT raises SQLException")
  void shouldThrowPersistenceExceptionWhenInsertFails() throws SQLException {
    // Arrange
    when(connection.prepareStatement(anyString())).thenReturn(statement);
    when(statement.executeUpdate()).thenThrow(new SQLException("Insert failed"));

    // Act + Assert
    assertThrows(PersistenceException.class, () -> repository.save(radio));
  }

  @Test
  @DisplayName("save() throws RadioNoEncontradoException when the saved radio cannot be found")
  void shouldThrowNotFoundWhenRadioMissingAfterSave() throws SQLException {
    // Arrange
    configureStatementAndResultSet();
    when(resultSet.next()).thenReturn(false);

    // Act + Assert
    assertThrows(RadioNoEncontradoException.class, () -> repository.save(radio));
  }

  // -- update()

  @Test
  @DisplayName("update() executes UPDATE and returns the updated radio")
  void shouldUpdateRadioAndReturnById() throws SQLException {
    // Arrange
    configureStatementAndResultSet();
    when(resultSet.next()).thenReturn(true);
    configureResultSetRow();

    // Act
    final Radio result = repository.update(radio);

    // Assert
    assertEquals(ID, result.id().valor());
    verify(statement).setString(4, ID);
  }

  @Test
  @DisplayName("update() throws PersistenceException when UPDATE raises SQLException")
  void shouldThrowPersistenceExceptionWhenUpdateFails() throws SQLException {
    // Arrange
    when(connection.prepareStatement(anyString())).thenReturn(statement);
    when(statement.executeUpdate()).thenThrow(new SQLException("Update failed"));

    // Act + Assert
    assertThrows(PersistenceException.class, () -> repository.update(radio));
  }

  // -- getById()

  @Test
  @DisplayName("getById() returns the radio when the row exists")
  void shouldReturnRadioWhenFound() throws SQLException {
    // Arrange
    configureStatementAndResultSet();
    when(resultSet.next()).thenReturn(true);
    configureResultSetRow();

    // Act
    final Optional<Radio> result = repository.getById(radioId);

    // Assert
    assertTrue(result.isPresent());
    assertEquals(NAME, result.get().nombre().valor());
  }

  @Test
  @DisplayName("getById() returns empty when no row matches")
  void shouldReturnEmptyWhenNotFound() throws SQLException {
    // Arrange
    configureStatementAndResultSet();
    when(resultSet.next()).thenReturn(false);

    // Act + Assert
    assertTrue(repository.getById(radioId).isEmpty());
  }

  @Test
  @DisplayName("getById() throws PersistenceException when the query raises SQLException")
  void shouldThrowPersistenceExceptionWhenFindByIdFails() throws SQLException {
    // Arrange
    when(connection.prepareStatement(anyString())).thenThrow(new SQLException("Query failed"));

    // Act + Assert
    assertThrows(PersistenceException.class, () -> repository.getById(radioId));
  }

  // -- getAll()

  @Test
  @DisplayName("getAll() returns every radio found")
  void shouldReturnAllRadios() throws SQLException {
    // Arrange
    configureStatementAndResultSet();
    when(resultSet.next()).thenReturn(true, true, false);
    configureResultSetRow();

    // Act
    final List<Radio> result = repository.getAll();

    // Assert
    assertEquals(2, result.size());
  }

  @Test
  @DisplayName("getAll() throws PersistenceException when the query raises SQLException")
  void shouldThrowPersistenceExceptionWhenFindAllFails() throws SQLException {
    // Arrange
    when(connection.prepareStatement(anyString())).thenThrow(new SQLException("Query failed"));

    // Act + Assert
    assertThrows(PersistenceException.class, () -> repository.getAll());
  }

  // -- delete()

  @Test
  @DisplayName("delete() executes DELETE with the radio id")
  void shouldDeleteRadio() throws SQLException {
    // Arrange
    when(connection.prepareStatement(anyString())).thenReturn(statement);

    // Act
    repository.delete(radioId);

    // Assert
    verify(statement).setString(1, ID);
    verify(statement).executeUpdate();
  }

  @Test
  @DisplayName("delete() throws PersistenceException when DELETE raises SQLException")
  void shouldThrowPersistenceExceptionWhenDeleteFails() throws SQLException {
    // Arrange
    when(connection.prepareStatement(anyString())).thenReturn(statement);
    when(statement.executeUpdate()).thenThrow(new SQLException("Delete failed"));

    // Act + Assert
    assertThrows(PersistenceException.class, () -> repository.delete(radioId));
  }
}
