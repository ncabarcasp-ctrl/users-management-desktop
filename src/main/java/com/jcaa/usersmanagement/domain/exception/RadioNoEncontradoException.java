package com.jcaa.usersmanagement.domain.exception;

public final class RadioNoEncontradoException extends DomainException {

  private static final String MESSAGE_BY_ID = "The radio with id '%s' was not found.";

  private RadioNoEncontradoException(final String message) {
    super(message);
  }

  public static RadioNoEncontradoException becauseIdWasNotFound(final String radioId) {
    return new RadioNoEncontradoException(String.format(MESSAGE_BY_ID, radioId));
  }
}
