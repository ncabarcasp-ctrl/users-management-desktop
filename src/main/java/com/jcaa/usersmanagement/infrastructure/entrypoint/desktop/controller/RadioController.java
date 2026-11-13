package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller;

import com.jcaa.usersmanagement.application.port.in.CreateRadioUseCase;
import com.jcaa.usersmanagement.application.port.in.DeleteRadioUseCase;
import com.jcaa.usersmanagement.application.port.in.GetAllRadiosUseCase;
import com.jcaa.usersmanagement.application.port.in.GetRadioByIdUseCase;
import com.jcaa.usersmanagement.application.port.in.UpdateRadioUseCase;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.CreateRadioRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.RadioResponse;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.UpdateRadioRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.mapper.RadioDesktopMapper;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public final class RadioController {

  private final CreateRadioUseCase createRadioUseCase;
  private final UpdateRadioUseCase updateRadioUseCase;
  private final DeleteRadioUseCase deleteRadioUseCase;
  private final GetRadioByIdUseCase getRadioByIdUseCase;
  private final GetAllRadiosUseCase getAllRadiosUseCase;

  public List<RadioResponse> listAllRadios() {
    final var radios = getAllRadiosUseCase.execute();
    return RadioDesktopMapper.toResponseList(radios);
  }

  public RadioResponse findRadioById(final String id) {
    final var query = RadioDesktopMapper.toGetByIdQuery(id);
    final var radio = getRadioByIdUseCase.execute(query);
    return RadioDesktopMapper.toResponse(radio);
  }

  public RadioResponse createRadio(final CreateRadioRequest request) {
    final var command = RadioDesktopMapper.toCreateCommand(request);
    final var radio = createRadioUseCase.execute(command);
    return RadioDesktopMapper.toResponse(radio);
  }

  public RadioResponse updateRadio(final UpdateRadioRequest request) {
    final var command = RadioDesktopMapper.toUpdateCommand(request);
    final var radio = updateRadioUseCase.execute(command);
    return RadioDesktopMapper.toResponse(radio);
  }

  public void deleteRadio(final String id) {
    final var command = RadioDesktopMapper.toDeleteCommand(id);
    deleteRadioUseCase.execute(command);
  }
}
