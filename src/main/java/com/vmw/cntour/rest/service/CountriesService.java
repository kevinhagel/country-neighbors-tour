package com.vmw.cntour.rest.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vmw.cntour.model.CountryInfo;
import com.vmw.cntour.model.ExchangeRates;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * CountriesService provides access to the countries data, excahnges, etc.  The countries information is laoded once at
 * the start of the application, countries don't change very often.
 *
 * @author Kevin Hagel
 * @since 2021-07-12
 */
@Service
@Slf4j
public class CountriesService {

  @Autowired
  private WebClient rapidApiCountriesClient;
  @Autowired
  private ObjectMapper objectMapper;
  private ConcurrentHashMap<String, CountryInfo> countriesCache = new ConcurrentHashMap<>();

  @Value("classpath:rest-countries.json")
  private Resource restCountriesJson;



  @PostConstruct
  public void init() throws Exception {
//    exchangeRatesClient
//        .get()
//        .uri(uriBuilder -> uriBuilder
//            .path("/latest")
//            .queryParam("access_key", appConfigurationProperties.getExchangeRatesAPIAccessKey())
//            .build())
//        .retrieve()
//        .bodyToMono(ExchangeRates.class)
//        .doOnSuccess(success -> System.out.println("success = " + success))
//        .subscribe(exchangeRates -> exchangeRatesCache = exchangeRates)
//    ;
    TypeReference<List<CountryInfo>> ref = new TypeReference<List<CountryInfo>>() {};

    List<CountryInfo> countries = objectMapper.readValue(restCountriesJson.getInputStream(), ref);
    countries.stream().forEach(country -> countriesCache.put(country.getAlpha3Code(), country));
  }

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

    Optional<CountryInfo> foundCountry = Optional.ofNullable(countriesCache.get(alpha3Code));

//    List<CountryInfo> list = rapidApiCountriesClient
//        .get()
//        .retrieve()
//        .bodyToFlux(CountryInfo.class)
//        .collectList().block();
//    Optional<CountryInfo> foundCountry = list
//        .stream()
//        .filter(ci -> ci.getAlpha3Code().equalsIgnoreCase(alpha3Code))
//        .findFirst();

    if (foundCountry.isEmpty()) {
      return Flux.empty();
    }
    List<String> borders = foundCountry.get().getBorders();
    return Flux.fromStream(borders.stream()).map(borderCountry -> countriesCache.get(borderCountry)).filter(
        Objects::nonNull);
//    return Flux.fromStream(list.stream().filter(countryInfo -> borders.contains(countryInfo.getAlpha3Code())));


  }

}
