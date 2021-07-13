package com.vmw.cntour.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * TourCountry an element in the tour, this pojo describes the country, the currency it uses, the amount of money
 * the user can spend of that.
 *
 * @author Kevin Hagel
 * @since 2021-07-12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TourCountry {

  private String country;
  private String currency;
  private BigDecimal budget;

}
