package com.jcaa.usersmanagement.infrastructure.adapter.persistence.mapper;

import com.jcaa.usersmanagement.domain.enums.TipoTransmision;
import com.jcaa.usersmanagement.domain.model.Radio;
import com.jcaa.usersmanagement.domain.valueobject.Frecuencia;
import com.jcaa.usersmanagement.domain.valueobject.NombreRadio;
import com.jcaa.usersmanagement.domain.valueobject.RadioId;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.entity.RadioEntity;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RadioPersistenceMapper {

  public RadioEntity fromModelToEntity(final Radio radio) {
    return new RadioEntity(
        radio.id().valor(),
        radio.nombre().valor(),
        radio.frecuencia().valor(),
        radio.tipoTransmision().name());
  }

  public Radio fromEntityToModel(final RadioEntity entity) {
    return new Radio(
        new RadioId(entity.id()),
        new NombreRadio(entity.name()),
        new Frecuencia(entity.frequency()),
        TipoTransmision.desde(entity.transmissionType()));
  }

  public RadioEntity fromResultSetToEntity(final ResultSet resultSet) throws SQLException {
    return new RadioEntity(
        resultSet.getString("id"),
        resultSet.getString("name"),
        resultSet.getDouble("frequency"),
        resultSet.getString("transmission_type"));
  }

  public Radio fromResultSetToModel(final ResultSet resultSet) throws SQLException {
    return fromEntityToModel(fromResultSetToEntity(resultSet));
  }

  public List<Radio> fromResultSetToModelList(final ResultSet resultSet) throws SQLException {
    final List<Radio> radios = new ArrayList<>();
    while (resultSet.next()) {
      radios.add(fromResultSetToModel(resultSet));
    }
    return radios;
  }
}