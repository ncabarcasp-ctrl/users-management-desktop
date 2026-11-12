package com.jcaa.usersmanagement.application.service.mapper;

import com.jcaa.usersmanagement.application.service.dto.command.CreateRadioCommand;
import com.jcaa.usersmanagement.application.service.dto.command.DeleteRadioCommand;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateRadioCommand;
import com.jcaa.usersmanagement.application.service.dto.query.GetRadioByIdQuery;
import com.jcaa.usersmanagement.domain.enums.TipoTransmision;
import com.jcaa.usersmanagement.domain.model.Radio;
import com.jcaa.usersmanagement.domain.valueobject.Frecuencia;
import com.jcaa.usersmanagement.domain.valueobject.NombreRadio;
import com.jcaa.usersmanagement.domain.valueobject.RadioId;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RadioApplicationMapper {

  public Radio fromCreateCommandToModel(final CreateRadioCommand command) {
    return Radio.registrar(
        new NombreRadio(command.name()),
        Frecuencia.desde(command.frequency()),
        TipoTransmision.desde(command.transmissionType()));
  }

  public void applyUpdateCommandToModel(final Radio radio, final UpdateRadioCommand command) {
    radio.actualizarDatos(
        new NombreRadio(command.name()),
        Frecuencia.desde(command.frequency()),
        TipoTransmision.desde(command.transmissionType()));
  }

  public RadioId fromUpdateCommandToRadioId(final UpdateRadioCommand command) {
    return new RadioId(command.id());
  }

  public RadioId fromGetRadioByIdQueryToRadioId(final GetRadioByIdQuery query) {
    return new RadioId(query.id());
  }

  public RadioId fromDeleteCommandToRadioId(final DeleteRadioCommand command) {
    return new RadioId(command.id());
  }
}
