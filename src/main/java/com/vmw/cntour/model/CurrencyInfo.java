package com.vmw.cntour.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * CurrencyInfo
 *
 * @author Kevin Hagel
 * @since 2021-07-18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CurrencyInfo {
  /** three digit iso code */
  private String isoCode;
  private String currency;
  private String country;
}
