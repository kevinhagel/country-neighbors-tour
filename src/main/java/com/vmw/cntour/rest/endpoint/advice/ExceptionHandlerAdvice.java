package com.vmw.cntour.rest.endpoint.advice;

import com.vmw.cntour.rest.CountryCodeNotFoundException;
import com.vmw.cntour.rest.NoBordersException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * ExceptionHandlerAdvice
 *
 * @author Kevin Hagel
 * @since 2021-07-13
 */
@ControllerAdvice
@Slf4j
public class ExceptionHandlerAdvice {

  @ExceptionHandler(NoBordersException.class)
  @ResponseBody
  public ResponseEntity<String> handleNoBordersException(NoBordersException exception) {
    log.error("NoBordersException", exception);
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(exception.getMessage());
  }

  @ExceptionHandler(CountryCodeNotFoundException.class)
  @ResponseBody
  public ResponseEntity<String> handleCountryCodeNotFoundException(CountryCodeNotFoundException exception) {
    log.error("CountryCodeNotFoundException", exception);
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(exception.getMessage());
  }

}
