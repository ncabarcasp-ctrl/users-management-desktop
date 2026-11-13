package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.mapper;

import com.jcaa.usersmanagement.application.service.dto.command.CreateRadioCommand;
import com.jcaa.usersmanagement.application.service.dto.command.DeleteRadioCommand;
import com.jcaa.usersmanagement.application.service.dto.command.UpdateRadioCommand;
import com.jcaa.usersmanagement.application.service.dto.query.GetRadioByIdQuery;
import com.jcaa.usersmanagement.domain.model.Radio;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.CreateRadioRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.RadioResponse;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.UpdateRadioRequest;

import java.util.List;

public final class RadioDesktopMapper {

  private RadioDesktopMapper() {}

  public static CreateRadioCommand toCreateCommand(final CreateRadioRequest request) {
    return new CreateRadioCommand(request.name(), request.frequency(), request.transmissionType());
  }

  public static UpdateRadioCommand toUpdateCommand(final UpdateRadioRequest request) {
    return new UpdateRadioCommand(
        request.id(), request.name(), request.frequency(), request.transmissionType());
  }

  public static DeleteRadioCommand toDeleteCommand(final String id) {
    return new DeleteRadioCommand(id);
  }

  public static GetRadioByIdQuery toGetByIdQuery(final String id) {
    return new GetRadioByIdQuery(id);
  }

  public static RadioResponse toResponse(final Radio radio) {
    return new RadioResponse(
        radio.id().valor(),
        radio.nombre().valor(),
        radio.frecuencia().toString(),
        radio.tipoTransmision().name());
  }

  public static List<RadioResponse> toResponseList(final List<Radio> radios) {
    return radios.stream().map(RadioDesktopMapper::toResponse).toList();
  }
}
