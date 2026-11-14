package com.jcaa.usersmanagement.infrastructure.adapter.persistence.repository;

import com.jcaa.usersmanagement.application.port.out.DeleteRadioPort;
import com.jcaa.usersmanagement.application.port.out.GetAllRadiosPort;
import com.jcaa.usersmanagement.application.port.out.GetRadioByIdPort;
import com.jcaa.usersmanagement.application.port.out.SaveRadioPort;
import com.jcaa.usersmanagement.application.port.out.UpdateRadioPort;
import com.jcaa.usersmanagement.domain.exception.RadioNoEncontradoException;
import com.jcaa.usersmanagement.domain.model.Radio;
import com.jcaa.usersmanagement.domain.valueobject.RadioId;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.entity.RadioEntity;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.exception.PersistenceException;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.mapper.RadioPersistenceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Log
@RequiredArgsConstructor
public final class RadioRepositoryMySQL
    implements SaveRadioPort,
        UpdateRadioPort,
        GetRadioByIdPort,
        GetAllRadiosPort,
        DeleteRadioPort {

  private static final String SQL_INSERT =
      "INSERT INTO radios "
      + "(id, name, frequency, transmission_type, created_at, updated_at) "
      + "VALUES (?, ?, ?, ?, NOW(), NOW())";

  private static final String SQL_UPDATE =
      "UPDATE radios SET name = ?, frequency = ?, transmission_type = ?, updated_at = NOW() "
      + "WHERE id = ?";

  private static final String SQL_SELECT_BY_ID =
      "SELECT id, name, frequency, transmission_type "
      + "FROM radios "
      + "WHERE id = ? LIMIT 1";

  private static final String SQL_SELECT_ALL =
      "SELECT id, name, frequency, transmission_type "
      + "FROM radios "
      + "ORDER BY name ASC";

  private static final String SQL_DELETE =
      "DELETE FROM radios "
      + "WHERE id = ?";

  private final Connection connection;

  @Override
  public Radio save(final Radio radio) {
    final RadioEntity entity = RadioPersistenceMapper.fromModelToEntity(radio);
    executeSave(entity);
    return findByIdOrFail(radio.id());
  }

  @Override
  public Radio update(final Radio radio) {
    final RadioEntity entity = RadioPersistenceMapper.fromModelToEntity(radio);
    executeUpdate(entity);
    return findByIdOrFail(radio.id());
  }

  @Override
  public Optional<Radio> getById(final RadioId radioId) {
    try (final PreparedStatement statement = connection.prepareStatement(SQL_SELECT_BY_ID)) {
      statement.setString(1, radioId.valor());
      final ResultSet resultSet = statement.executeQuery();
      if (!resultSet.next()) {
        return Optional.empty();
      }
      return Optional.of(RadioPersistenceMapper.fromResultSetToModel(resultSet));
    } catch (final SQLException exception) {
      throw PersistenceException.becauseRadioFindByIdFailed(radioId.valor(), exception);
    }
  }

  @Override
  public List<Radio> getAll() {
    try (final PreparedStatement statement = connection.prepareStatement(SQL_SELECT_ALL)) {
      final ResultSet resultSet = statement.executeQuery();
      return RadioPersistenceMapper.fromResultSetToModelList(resultSet);
    } catch (final SQLException exception) {
      throw PersistenceException.becauseRadioFindAllFailed(exception);
    }
  }

  @Override
  public void delete(final RadioId radioId) {
    try (final PreparedStatement statement = connection.prepareStatement(SQL_DELETE)) {
      statement.setString(1, radioId.valor());
      statement.executeUpdate();
    } catch (final SQLException exception) {
      throw PersistenceException.becauseRadioDeleteFailed(radioId.valor(), exception);
    }
  }

  private void executeSave(final RadioEntity entity) {
    try (final PreparedStatement statement = connection.prepareStatement(SQL_INSERT)) {
      statement.setString(1, entity.id());
      statement.setString(2, entity.name());
      statement.setDouble(3, entity.frequency());
      statement.setString(4, entity.transmissionType());
      statement.executeUpdate();
    } catch (final SQLException exception) {
      throw PersistenceException.becauseRadioSaveFailed(entity.id(), exception);
    }
  }

  private void executeUpdate(final RadioEntity entity) {
    try (final PreparedStatement statement = connection.prepareStatement(SQL_UPDATE)) {
      statement.setString(1, entity.name());
      statement.setDouble(2, entity.frequency());
      statement.setString(3, entity.transmissionType());
      statement.setString(4, entity.id());
      statement.executeUpdate();
    } catch (final SQLException exception) {
      throw PersistenceException.becauseRadioUpdateFailed(entity.id(), exception);
    }
  }

  private Radio findByIdOrFail(final RadioId radioId) {
    return getById(radioId)
        .orElseThrow(() -> RadioNoEncontradoException.becauseIdWasNotFound(radioId.valor()));
  }
}