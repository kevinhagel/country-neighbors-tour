package com.vmw.cntour.rest;

import lombok.Data;

/**
 * CountryCodeNotFoundException country code or country spec could not be found.
 *
 * @author Kevin Hagel
 * @since 2021-07-13
 */
@Data
public class CountryCodeNotFoundException extends RuntimeException {

  private final String message;

  public CountryCodeNotFoundException(String message) {this.message = message;}
}
