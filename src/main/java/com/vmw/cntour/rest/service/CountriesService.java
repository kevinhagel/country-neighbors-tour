package com.vmw.cntour.rest.service;

import com.vmw.cntour.model.CountryInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * CountriesService provides access to the countries data, excahnges, etc.
 *
 * @author Kevin Hagel
 * @since 2021-07-12
 */
@Service
@Slf4j
public class CountriesService {

  @Autowired
  private WebClient rapidApiCountriesClient;


  /**
   * Get the raw countries data.
   *
   * @return the raw countries info data, containing the minimal stuff we need.
   */
  public Flux<CountryInfo> getAllCountries() {
    return rapidApiCountriesClient
        .get()
        .retrieve()
        .bodyToFlux(CountryInfo.class);
  }

  /**
   * Get the country info by alpha3code.
   *
   * @param alpha3Code the alpha3 code of the country.
   * @return the country info, possibly empty if you sent me crap.
   */
  public Mono<CountryInfo> getCountry(String alpha3Code) {
    return rapidApiCountriesClient
        .get()
        .retrieve()
        .bodyToFlux(CountryInfo.class)
        .filter(ci -> ci.getAlpha3Code().equals(alpha3Code))
        .next();
  }

  /**
   * Find the border countries for the given alpha3code, if any.
   *
   * @param alpha3Code
   * @return
   */
  public Flux<CountryInfo> getBorderCountries(String alpha3Code) {
    List<CountryInfo> list = rapidApiCountriesClient
        .get()
        .retrieve()
        .bodyToFlux(CountryInfo.class)
        .collectList().block();
    Optional<CountryInfo> foundCountry = list
        .stream()
        .filter(ci -> ci.getAlpha3Code().equalsIgnoreCase(alpha3Code))
        .findFirst();

    if (foundCountry.isEmpty()) {
      return Flux.empty();
    }
    List<String> borders = foundCountry.get().getBorders();
    return Flux.fromStream(list.stream().filter(countryInfo -> borders.contains(countryInfo.getAlpha3Code())));


  }

}
