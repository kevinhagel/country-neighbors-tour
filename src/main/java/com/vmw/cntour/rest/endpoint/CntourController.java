package com.vmw.cntour.rest.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vmw.cntour.model.*;
import com.vmw.cntour.rest.service.CountriesService;
import com.vmw.cntour.rest.service.CurrenciesMapper;
import com.vmw.cntour.rest.service.TourService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.springframework.util.StringUtils.startsWithIgnoreCase;

/**
 * CntourController
 *
 * @author Kevin Hagel
 * @since 2021-07-12
 */
@RestController
@RequestMapping("/api/v1")
@Slf4j
public class CntourController {
  @Autowired
  private TourService tourService;

  @Autowired
  private CountriesService countriesService;

  @Autowired
  private CurrenciesMapper currenciesMapper;

  @Autowired
  private ObjectMapper objectMapper;

  /**
   * User is requesting a tour.
   *
   * @param tourRequest the request arriving from the client.
   * @return a tour response.
   */
  @PostMapping(value = "/requestTour")
  @CrossOrigin(origins = {"*"})
  public TourResponse requestTour(@RequestBody TourRequest tourRequest) throws Exception {
    log.info("tour request {}", tourRequest);
    TourResponse tourResponse = tourService.buildTour(tourRequest);
    log.info("tourResponse = " + tourResponse);
    return tourResponse;
//    return ResponseEntity.ok(tourResponse);
  }

  @SneakyThrows
  @GetMapping("/oidc-principal")
  @CrossOrigin
  public String getOidcUserPrincipal(
      @AuthenticationPrincipal OidcUser principal) {
    return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(principal);
  }

  @GetMapping("/countries")
  @CrossOrigin
  public List<CountryInfo> countries(@RequestParam(value = "term", required = false, defaultValue = "") String term) {
    System.out.println("term = " + term);
    return countriesService.findAll()
        .stream()
        .filter(countryInfo -> startsWithIgnoreCase(countryInfo.getName(), term))
        .peek(c -> System.out.println(c))
        .collect(Collectors.toList());
  }

  /**
   * returns only the country names in a {@link Select2Results} list.
   *
   * @return
   */
  @GetMapping("/countryNames")
  @CrossOrigin
  public Mono<ResponseEntity<Select2Results>> countryNames(
      @RequestParam(value = "q", required = false) Optional<String> query) {
    System.out.println("query = " + query);
    Predicate<CountryInfo> queryFilter = countryInfo -> query
        .map(q -> startsWithIgnoreCase(countryInfo.getName(), q)).orElse(true);
    List<Select2Results.Result> countryNames = countriesService
        .findAll()
        .stream()
        .filter(queryFilter)
        .sorted((o1, o2) -> o1.getName().compareTo(o2.getName()))
        .map(countryInfo -> Select2Results.Result.builder()
            .id(countryInfo.getAlpha3Code())
            .text(countryInfo.getName())
            .build())
        .collect(Collectors.toList());
    return Mono.just(ResponseEntity.ok(Select2Results.builder().results(countryNames).build()));

  }


  /**
   * returns only the country names in a {@link Select2Results} list.
   *
   * @return
   */
  @GetMapping("/currencyNames")
  @CrossOrigin
  public Mono<ResponseEntity<Select2Results>> currencyNames(
      @RequestParam(value = "q", required = false) Optional<String> query) {
    System.out.println("query = " + query);

    Predicate<CurrencyInfo> queryFilter = ci -> query
        .map(q -> startsWithIgnoreCase(ci.getCurrency(), q) || startsWithIgnoreCase(ci.getCurrency(),
            q)).orElse(true);

    List<Select2Results.Result> currencyNames = currenciesMapper
        .values()
        .stream()
        .filter(queryFilter)
        .sorted(((o1, o2) -> o1.getCountry().compareToIgnoreCase(o2.getCountry())))
        .map(currencyInfo -> Select2Results.Result.builder()
            .id(currencyInfo.getIsoCode())
            .text(currencyInfo.getCurrency())
            .build())
        .collect(Collectors.toList());


    return Mono.just(ResponseEntity.ok(Select2Results.builder().results(currencyNames).build()));

  }


}

