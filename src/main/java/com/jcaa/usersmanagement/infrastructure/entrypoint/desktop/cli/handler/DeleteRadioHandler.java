package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler;

import com.jcaa.usersmanagement.domain.exception.RadioNoEncontradoException;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.RadioController;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class DeleteRadioHandler implements OperationHandler {

  private final RadioController radioController;
  private final ConsoleIO console;

  @Override
  public void handle() {
    final String id = console.readRequired("Radio ID to delete: ");
    try {
      radioController.deleteRadio(id);
      console.println("  Radio deleted successfully.");
    } catch (final RadioNoEncontradoException exception) {
      console.println("  Not found: " + exception.getMessage());
    }
  }
}
