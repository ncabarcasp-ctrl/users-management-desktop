package com.jcaa.usersmanagement.infrastructure.adapter.persistence.repository;

import com.jcaa.usersmanagement.application.port.out.DeleteRadioPort;
import com.jcaa.usersmanagement.application.port.out.GetAllRadiosPort;
import com.jcaa.usersmanagement.application.port.out.GetRadioByIdPort;
import com.jcaa.usersmanagement.application.port.out.SaveRadioPort;
import com.jcaa.usersmanagement.application.port.out.UpdateRadioPort;
import com.jcaa.usersmanagement.domain.model.Radio;
import com.jcaa.usersmanagement.domain.valueobject.RadioId;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.entity.RadioEntity;
import com.jcaa.usersmanagement.infrastructure.adapter.persistence.mapper.RadioPersistenceMapper;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * In-memory adapter for the radio persistence ports. Data lives only while the application runs.
 */
public final class RadioRepositoryInMemory
    implements SaveRadioPort,
        UpdateRadioPort,
        GetRadioByIdPort,
        GetAllRadiosPort,
        DeleteRadioPort {

  private final Map<String, RadioEntity> storage = new ConcurrentHashMap<>();

  @Override
  public Radio save(final Radio radio) {
    return store(radio);
  }

  @Override
  public Radio update(final Radio radio) {
    return store(radio);
  }

  @Override
  public Optional<Radio> getById(final RadioId radioId) {
    return Optional.ofNullable(storage.get(radioId.valor()))
        .map(RadioPersistenceMapper::fromEntityToModel);
  }

  @Override
  public List<Radio> getAll() {
    return storage.values().stream()
        .map(RadioPersistenceMapper::fromEntityToModel)
        .sorted(Comparator.comparing(radio -> radio.nombre().valor(), String.CASE_INSENSITIVE_ORDER))
        .toList();
  }

  @Override
  public void delete(final RadioId radioId) {
    storage.remove(radioId.valor());
  }

  private Radio store(final Radio radio) {
    final RadioEntity entity = RadioPersistenceMapper.fromModelToEntity(radio);
    storage.put(entity.id(), entity);
    return RadioPersistenceMapper.fromEntityToModel(entity);
  }
}
