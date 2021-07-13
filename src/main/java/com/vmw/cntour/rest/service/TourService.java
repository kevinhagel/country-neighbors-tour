package com.vmw.cntour.rest.service;

import com.vmw.cntour.model.CountryInfo;
import com.vmw.cntour.model.TourCountry;
import com.vmw.cntour.model.TourRequest;
import com.vmw.cntour.model.TourResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
   * @param tourRequest
   * @return
   */
  public Mono<TourResponse> buildTour(TourRequest tourRequest) throws Exception {

    Flux<CountryInfo> borderCountriesFlux = countriesService.getBorderCountries(tourRequest.getStartingCountry());


    List<CountryInfo> borderCountries = borderCountriesFlux.toStream().collect(Collectors.toList());
    if (borderCountries.isEmpty()) {
      return Mono.error(new Exception("unknown country " + tourRequest.getStartingCountry()));
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


    TourResponse tourResponse = TourResponse
        .builder()
        .totalBudget(tourRequest.getTotalBudget())
        .numberOfTours(numberTours.intValue())
        .currency(tourRequest.getCurrency())
        .remainder(leftoverAmount)
        .startCountry(tourRequest.getStartingCountry())
        .tourCountryList(tourCountryList)
        .build();
    ;

    return Mono.justOrEmpty(tourResponse);

  }
}
