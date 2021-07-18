package com.vmw.cntour.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * TourResponse the response pojo for the user's tour request.
 *
 * @author Kevin Hagel
 * @since 2021-07-12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TourResponse {

  private String startingCountry;
  private String startingCountryName;
  private String budgetCurrency;
  private String budgetCurrencyName;
  private BigDecimal totalBudget;
  private BigDecimal budgetPerCountry;
  private BigDecimal remainder;
  private Integer numberOfTours;
  @Builder.Default
  private List<TourCountry> tourCountryList = new ArrayList<>();

}
