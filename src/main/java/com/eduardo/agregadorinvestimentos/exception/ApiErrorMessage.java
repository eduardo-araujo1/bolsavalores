package com.eduardo.agregadorinvestimentos.exception;

import org.springframework.http.HttpStatus;

public record ApiErrorMessage(HttpStatus httpStatus, String message) {
}
