package com.vmw.cntour.rest;

import lombok.Data;

/**
 * NoBordersException some countries, Islands for example, do not have any borders.
 *
 * @author Kevin Hagel
 * @since 2021-07-13
 */
@Data
public class NoBordersException extends RuntimeException {

  private final String message;

  public NoBordersException(String message) {this.message = message;}
}
