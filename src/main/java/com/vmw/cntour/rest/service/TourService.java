package com.vmw.cntour.rest.service;

import com.vmw.cntour.model.CountryInfo;
import com.vmw.cntour.model.TourCountry;
import com.vmw.cntour.model.TourRequest;
import com.vmw.cntour.model.TourResponse;
import com.vmw.cntour.rest.CountryCodeNotFoundException;
import com.vmw.cntour.rest.NoBordersException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TourService provides the intersection service betewwen the countries, the currencies, etc.
 *
 * @author Kevin Hagel
 * @since 2021-07-12
 */
@Service
@Slf4j
public class TourService {
  @Autowired
  private CountriesService countriesService;

  @Autowired
  private ExchangeRatesService exchangeRatesService;


  /**
   * Build the tour.  First we need to locate the border countries of the given home country;  Find their currencies,
   * get the exchange rates ec.
   *
   * @param tourRequest the tour request information.
   * @return a tour response.
   * @throws Exception for any needed exception.
   */
  public TourResponse buildTour(TourRequest tourRequest) throws Exception {

    CountryInfo startingCountry = countriesService
        .findCountry(tourRequest.getStartingCountry())
        .orElseThrow(() -> new CountryCodeNotFoundException("unknown country " + tourRequest.getStartingCountry()));

    List<CountryInfo> borderCountries = countriesService.getBorderCountries(startingCountry);
    if (borderCountries.isEmpty()) {
      throw new NoBordersException("no border countries for " + startingCountry.getName());
    }


    /* calculate the number of tours and the leftover amount, we can do that now. */
    int numberCountries = borderCountries.size();
    BigDecimal oneTourCost = tourRequest.getBudgetPerCountry().multiply(BigDecimal.valueOf(numberCountries));
    BigDecimal numberTours = tourRequest.getTotalBudget().divide(oneTourCost, 0, RoundingMode.DOWN);
    BigDecimal leftoverAmount = tourRequest.getTotalBudget().subtract(oneTourCost.multiply(numberTours));

    /* ok, now we need to calculate the exchange values for each country in the tour */

    List<TourCountry> tourCountryList = borderCountries
        .stream()
        .map(borderCountry -> {
          String toCurrency = borderCountry.getCurrencies().stream().findFirst().orElse(tourRequest.getCurrency());
          BigDecimal budget = exchangeRatesService
              .exchangeConversion(tourRequest.getCurrency(), toCurrency, tourRequest.getBudgetPerCountry());
          return TourCountry
              .builder()
              .budget(budget)
              .country(borderCountry.getName())
              .currency(toCurrency)
              .build();
        })
        .collect(Collectors.toList());


    return TourResponse
        .builder()
        .totalBudget(tourRequest.getTotalBudget())
        .numberOfTours(numberTours.intValue())
        .currency(tourRequest.getCurrency())
        .remainder(leftoverAmount)
        .startCountry(tourRequest.getStartingCountry())
        .tourCountryList(tourCountryList)
        .build();

  }
}
