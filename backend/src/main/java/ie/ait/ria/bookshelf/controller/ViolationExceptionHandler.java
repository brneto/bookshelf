package ie.ait.ria.bookshelf.controller;

import ie.ait.ria.bookshelf.model.ValidationErrors;
import ie.ait.ria.bookshelf.model.ValidationErrors.Violation;
import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ViolationExceptionHandler {

  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  ValidationErrors handleValidationException(ConstraintViolationException e) {
    ValidationErrors errors = new ValidationErrors();
    e.getConstraintViolations().forEach(
        v -> errors.addViolation(new Violation(v.getPropertyPath().toString(), v.getMessage())));

    return errors;
  }
}
