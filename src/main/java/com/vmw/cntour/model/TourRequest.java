package com.vmw.cntour.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * TourRequest a pojo receiving the user's request.
 *
 * @author Kevin Hagel
 * @since 2021-07-12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TourRequest {

  private String startingCountry;
  private BigDecimal budgetPerCountry;
  private BigDecimal totalBudget;
  private String currency;

}
