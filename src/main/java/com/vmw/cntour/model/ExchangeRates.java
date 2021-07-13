package com.vmw.cntour.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * ExchangeRates the result of the "latest" exchange rates compared to the EUR.
 *
 * @author Kevin Hagel
 * @since 2021-07-12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExchangeRates {

  private boolean success;
  private String timestamp;
  private String base;
  private String date;
  private Map<String, BigDecimal> rates = new HashMap<>();


  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class Rate {
    private String alpha3Code;
    private BigDecimal euroConversion;
  }


}
