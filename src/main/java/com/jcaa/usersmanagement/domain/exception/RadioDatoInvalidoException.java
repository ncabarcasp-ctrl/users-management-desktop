package com.jcaa.usersmanagement.domain.exception;

public final class RadioDatoInvalidoException extends DomainException {

  private static final String MESSAGE_ID_EMPTY = "The radio id must not be empty.";
  private static final String MESSAGE_NAME_EMPTY = "The radio name must not be empty.";
  private static final String MESSAGE_NAME_TOO_LONG =
      "The radio name must not exceed %d characters.";
  private static final String MESSAGE_FREQUENCY_NOT_A_NUMBER =
      "The radio frequency '%s' is not a valid number.";
  private static final String MESSAGE_FREQUENCY_NOT_POSITIVE =
      "The radio frequency must be greater than zero, but was %s.";
  private static final String MESSAGE_TYPE_EMPTY = "The transmission type must not be empty.";
  private static final String MESSAGE_TYPE_INVALID =
      "The transmission type '%s' is not valid. Use AM or FM.";

  private RadioDatoInvalidoException(final String message) {
    super(message);
  }

  public static RadioDatoInvalidoException becauseIdIsEmpty() {
    return new RadioDatoInvalidoException(MESSAGE_ID_EMPTY);
  }

  public static RadioDatoInvalidoException becauseNameIsEmpty() {
    return new RadioDatoInvalidoException(MESSAGE_NAME_EMPTY);
  }

  public static RadioDatoInvalidoException becauseNameIsTooLong(final int maximumLength) {
    return new RadioDatoInvalidoException(String.format(MESSAGE_NAME_TOO_LONG, maximumLength));
  }

  public static RadioDatoInvalidoException becauseFrequencyIsNotANumber(final String value) {
    return new RadioDatoInvalidoException(String.format(MESSAGE_FREQUENCY_NOT_A_NUMBER, value));
  }

  public static RadioDatoInvalidoException becauseFrequencyIsNotPositive(final double value) {
    return new RadioDatoInvalidoException(String.format(MESSAGE_FREQUENCY_NOT_POSITIVE, value));
  }

  public static RadioDatoInvalidoException becauseTransmissionTypeIsEmpty() {
    return new RadioDatoInvalidoException(MESSAGE_TYPE_EMPTY);
  }

  public static RadioDatoInvalidoException becauseTransmissionTypeIsInvalid(final String value) {
    return new RadioDatoInvalidoException(String.format(MESSAGE_TYPE_INVALID, value));
  }
}
