package com.vmw.cntour.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * ExchangeConversionResponse value returned from the exchangerates api
 *
 * @author Kevin Hagel
 * @since 2021-07-12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExchangeConversionResponse {

  private boolean success;
  private Query query;
  private Info info;
  private String historical;
  private String date;
  private BigDecimal result;


  private static class Query {
    private String from;
    private String to;
    private BigDecimal amount;
  }

  private static class Info {
    private String timestamp;
    private BigDecimal rate;
  }

}
