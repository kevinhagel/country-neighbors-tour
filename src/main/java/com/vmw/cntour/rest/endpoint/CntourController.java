package com.vmw.cntour.rest.endpoint;

import com.vmw.cntour.model.*;
import com.vmw.cntour.rest.service.CountriesService;
import com.vmw.cntour.rest.service.ExchangeRatesService;
import com.vmw.cntour.rest.service.TourService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

/**
 * CntourController
 *
 * @author Kevin Hagel
 * @since 2021-07-12
 */
@RestController
@RequestMapping("/api/v1/")
@Slf4j
public class CntourController {

  @Autowired
  private CountriesService countriesService;


  @Autowired
  private TourService tourService;

  @Autowired
  private ExchangeRatesService exchangeRatesService;

  /**
   * User is requesting a tour.
   * @param tourRequest
   * @return
   */
  @PostMapping("requestTour")
  public Mono<TourResponse> requestTour(@RequestBody TourRequest tourRequest) {
    log.info("tour request {}", tourRequest);
    return tourService.buildTour(tourRequest);
  }

  @GetMapping("exchange")
  public Mono<BigDecimal> exchange(@RequestParam("from") String from, @RequestParam("to") String to, @RequestParam("amount")
      BigDecimal amount) {
    return Mono.just(exchangeRatesService.exchangeConversion(from, to, amount));

  }

  @GetMapping("country/{alpha3Code}")
  public Mono<CountryInfo> country(@NotBlank @PathVariable("alpha3Code") String alpha3Code) {
    return countriesService.getCountry(alpha3Code);
  }

  @GetMapping("country/{alpha3Code}/borders")
  public Flux<CountryInfo> borders(@NotBlank @PathVariable("alpha3Code") String alpha3Code) {
    return countriesService.getBorderCountries(alpha3Code);
  }




}
