package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.handler;

import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.ConsoleIO;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io.RadioResponsePrinter;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.controller.RadioController;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.CreateRadioRequest;
import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.RadioResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class CreateRadioHandler implements OperationHandler {

  private final RadioController radioController;
  private final ConsoleIO console;
  private final RadioResponsePrinter printer;

  @Override
  public void handle() {
    final String name      = console.readRequired("Name                  : ");
    final String frequency = console.readRequired("Frequency             : ");
    final String type      = console.readRequired("Type (AM / FM)        : ");

    final RadioResponse created =
        radioController.createRadio(new CreateRadioRequest(name, frequency, type));
    console.println("\n  Radio created successfully (the ID was generated automatically).");
    printer.print(created);
  }
}
