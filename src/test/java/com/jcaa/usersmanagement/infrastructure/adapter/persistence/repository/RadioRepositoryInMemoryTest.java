package com.jcaa.usersmanagement.infrastructure.adapter.persistence.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.jcaa.usersmanagement.domain.enums.TipoTransmision;
import com.jcaa.usersmanagement.domain.model.Radio;
import com.jcaa.usersmanagement.domain.valueobject.Frecuencia;
import com.jcaa.usersmanagement.domain.valueobject.NombreRadio;
import com.jcaa.usersmanagement.domain.valueobject.RadioId;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("RadioRepositoryInMemory")
class RadioRepositoryInMemoryTest {

  private RadioRepositoryInMemory repository;

  @BeforeEach
  void setUp() {
    repository = new RadioRepositoryInMemory();
  }

  private static Radio radio(final String id, final String name) {
    return new Radio(new RadioId(id), new NombreRadio(name), new Frecuencia(98.5), TipoTransmision.FM);
  }

  @Test
  @DisplayName("save() y getById() persisten y recuperan la radio")
  void shouldSaveAndFindById() {
    final Radio saved = repository.save(radio("r-001", "Tropicana"));

    final Radio found = repository.getById(new RadioId("r-001")).orElseThrow();

    assertEquals(saved, found);
    assertEquals("Tropicana", found.nombre().valor());
  }

  @Test
  @DisplayName("getById() retorna vacio cuando el id no existe")
  void shouldReturnEmptyWhenMissing() {
    assertTrue(repository.getById(new RadioId("no-existe")).isEmpty());
  }

  @Test
  @DisplayName("update() reemplaza los datos de la radio existente")
  void shouldUpdate() {
    repository.save(radio("r-001", "Tropicana"));

    repository.update(radio("r-001", "Nueva"));

    assertEquals("Nueva", repository.getById(new RadioId("r-001")).orElseThrow().nombre().valor());
  }

  @Test
  @DisplayName("delete() elimina la radio")
  void shouldDelete() {
    repository.save(radio("r-001", "Tropicana"));

    repository.delete(new RadioId("r-001"));

    assertTrue(repository.getById(new RadioId("r-001")).isEmpty());
  }

  @Test
  @DisplayName("getAll() retorna las radios ordenadas por nombre")
  void shouldListSortedByName() {
    repository.save(radio("r-002", "Zeta"));
    repository.save(radio("r-001", "Alfa"));

    final List<Radio> all = repository.getAll();

    assertEquals(List.of("Alfa", "Zeta"), all.stream().map(r -> r.nombre().valor()).toList());
  }
}
