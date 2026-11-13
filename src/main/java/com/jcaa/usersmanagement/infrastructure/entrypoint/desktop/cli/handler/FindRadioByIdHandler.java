package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler;

import com.jcaa.usersmanagement.domain.exception.RadioNoEncontradoException;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.RadioResponsePrinter;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.RadioController;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.RadioResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class FindRadioByIdHandler implements OperationHandler {

  private final RadioController radioController;
  private final ConsoleIO console;
  private final RadioResponsePrinter printer;

  @Override
  public void handle() {
    final String id = console.readRequired("Radio ID: ");
    try {
      final RadioResponse radio = radioController.findRadioById(id);
      printer.print(radio);
    } catch (final RadioNoEncontradoException exception) {
      console.println("  Not found: " + exception.getMessage());
    }
  }
}
