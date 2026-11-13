package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler;

import com.jcaa.usersmanagement.domain.exception.RadioNoEncontradoException;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.RadioResponsePrinter;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.RadioController;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.RadioResponse;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.UpdateRadioRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class UpdateRadioHandler implements OperationHandler {

  private final RadioController radioController;
  private final ConsoleIO console;
  private final RadioResponsePrinter printer;

  @Override
  public void handle() {
    final String id        = console.readRequired("Radio ID              : ");
    final String name      = console.readRequired("New name              : ");
    final String frequency = console.readRequired("New frequency         : ");
    final String type      = console.readRequired("Type (AM / FM)        : ");

    try {
      final RadioResponse updated =
          radioController.updateRadio(new UpdateRadioRequest(id, name, frequency, type));
      console.println("\n  Radio updated successfully.");
      printer.print(updated);
    } catch (final RadioNoEncontradoException exception) {
      console.println("  Not found: " + exception.getMessage());
    }
  }
}
