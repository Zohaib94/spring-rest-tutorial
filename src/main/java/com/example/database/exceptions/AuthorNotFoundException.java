package com.example.database.exceptions;

import jakarta.persistence.EntityNotFoundException;

public class AuthorNotFoundException extends EntityNotFoundException {
  public AuthorNotFoundException(String message) {
    super(message);
  }
}
