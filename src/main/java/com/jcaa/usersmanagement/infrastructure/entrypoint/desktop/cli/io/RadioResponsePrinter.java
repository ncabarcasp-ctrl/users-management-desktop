package com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.cli.io;

import com.jcaa.usersmanagement.infrastructure.entrypoint.desktop.dto.RadioResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class RadioResponsePrinter {

  private static final String SEPARATOR = "-".repeat(52);
  private static final String ROW_FORMAT = "  %-10s : %s%n";

  private final ConsoleIO console;

  public void print(final RadioResponse response) {
    console.println(SEPARATOR);
    console.printf(ROW_FORMAT, "ID",        response.id());
    console.printf(ROW_FORMAT, "Name",      response.name());
    console.printf(ROW_FORMAT, "Frequency", response.frequency() + " " + unitOf(response));
    console.printf(ROW_FORMAT, "Type",      response.transmissionType());
    console.println(SEPARATOR);
  }

  public void printList(final List<RadioResponse> radios) {
    if (radios.isEmpty()) {
      console.println("  No radios found.");
      return;
    }
    console.printf("%n  Total: %d radio(s)%n", radios.size());
    radios.forEach(this::print);
  }

  private static String unitOf(final RadioResponse response) {
    return "AM".equalsIgnoreCase(response.transmissionType()) ? "kHz" : "MHz";
  }
}
