package com.jcaa.usersmanagement.infrastructure.adapter.persistence.exception;

public final class PersistenceException extends RuntimeException {

  private static final String MESSAGE_SAVE = "Failed to save user with ID: '%s'.";
  private static final String MESSAGE_UPDATE = "Failed to update user with ID: '%s'.";
  private static final String MESSAGE_FIND = "Failed to find user with ID: '%s'.";
  private static final String MESSAGE_EMAIL = "Failed to find user with email: '%s'.";
  private static final String MESSAGE_ALL = "Failed to retrieve all users.";
  private static final String MESSAGE_DELETE = "Failed to delete user with ID: '%s'.";
  private static final String MESSAGE_CONNECTION = "Could not establish database connection.";
  private static final String MESSAGE_RADIO_SAVE = "Failed to save radio with ID: '%s'.";
  private static final String MESSAGE_RADIO_UPDATE = "Failed to update radio with ID: '%s'.";
  private static final String MESSAGE_RADIO_FIND = "Failed to find radio with ID: '%s'.";
  private static final String MESSAGE_RADIO_ALL = "Failed to retrieve all radios.";
  private static final String MESSAGE_RADIO_DELETE = "Failed to delete radio with ID: '%s'.";

  private PersistenceException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public static PersistenceException becauseSaveFailed(final String userId, final Throwable cause) {
    return new PersistenceException(String.format(MESSAGE_SAVE, userId), cause);
  }

  public static PersistenceException becauseUpdateFailed(
      final String userId, final Throwable cause) {
    return new PersistenceException(String.format(MESSAGE_UPDATE, userId), cause);
  }

  public static PersistenceException becauseFindByIdFailed(
      final String userId, final Throwable cause) {
    return new PersistenceException(String.format(MESSAGE_FIND, userId), cause);
  }

  public static PersistenceException becauseFindByEmailFailed(
      final String email, final Throwable cause) {
    return new PersistenceException(String.format(MESSAGE_EMAIL, email), cause);
  }

  public static PersistenceException becauseFindAllFailed(final Throwable cause) {
    return new PersistenceException(MESSAGE_ALL, cause);
  }

  public static PersistenceException becauseDeleteFailed(
      final String userId, final Throwable cause) {
    return new PersistenceException(String.format(MESSAGE_DELETE, userId), cause);
  }

  public static PersistenceException becauseConnectionFailed(final Throwable cause) {
    return new PersistenceException(MESSAGE_CONNECTION, cause);
  }

  public static PersistenceException becauseRadioSaveFailed(
      final String radioId, final Throwable cause) {
    return new PersistenceException(String.format(MESSAGE_RADIO_SAVE, radioId), cause);
  }

  public static PersistenceException becauseRadioUpdateFailed(
      final String radioId, final Throwable cause) {
    return new PersistenceException(String.format(MESSAGE_RADIO_UPDATE, radioId), cause);
  }

  public static PersistenceException becauseRadioFindByIdFailed(
      final String radioId, final Throwable cause) {
    return new PersistenceException(String.format(MESSAGE_RADIO_FIND, radioId), cause);
  }

  public static PersistenceException becauseRadioFindAllFailed(final Throwable cause) {
    return new PersistenceException(MESSAGE_RADIO_ALL, cause);
  }

  public static PersistenceException becauseRadioDeleteFailed(
      final String radioId, final Throwable cause) {
    return new PersistenceException(String.format(MESSAGE_RADIO_DELETE, radioId), cause);
  }
}