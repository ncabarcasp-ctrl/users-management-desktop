package com.jcaa.usersmanagement.application.service;

import com.jcaa.usersmanagement.application.port.in.GetAllRadiosUseCase;
import com.jcaa.usersmanagement.application.port.out.GetAllRadiosPort;
import com.jcaa.usersmanagement.domain.model.Radio;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public final class GetAllRadiosService implements GetAllRadiosUseCase {

  private final GetAllRadiosPort getAllRadiosPort;

  @Override
  public List<Radio> execute() {
    return getAllRadiosPort.getAll();
  }
}
