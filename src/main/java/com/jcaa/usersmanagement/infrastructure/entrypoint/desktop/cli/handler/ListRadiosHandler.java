package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler;

import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.RadioResponsePrinter;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.RadioController;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.RadioResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class ListRadiosHandler implements OperationHandler {

  private final RadioController radioController;
  private final RadioResponsePrinter printer;

  @Override
  public void handle() {
    final List<RadioResponse> radios = radioController.listAllRadios();
    printer.printList(radios);
  }
}
