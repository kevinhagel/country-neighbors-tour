package com.vmw.cntour.rest.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vmw.cntour.model.CountryInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.scheduler.Schedulers;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

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


  /**
   * Load the rapidApiCountries information we want, using a webclient.
   *
   * @throws Exception it could happen.
   */
  @PostConstruct
  public void init() throws Exception {
    rapidApiCountriesClient
        .get()
        .retrieve()
        .bodyToFlux(CountryInfo.class)
        .doOnNext(this::addCountry)
        .subscribeOn(Schedulers.boundedElastic())
        .doOnError(throwable -> log.error("error loading api countries list", throwable))
        .subscribe();
  }

  /**
   * Private method used to load the countries cache from the webclient call.
   *
   * @param country the ccuntry just parsed.
   */
  private void addCountry(CountryInfo country) {
    countriesCache.put(country.getAlpha3Code(), country);
  }


  /**
   * Try to find the country.  It might be that the user entered "Bulgaria" instead of BGR, let's try to protect them.
   *
   * @param countrySpec the search, hopefully is a correct AlphaCode3 value, this method will attempt to match a full
   *                    name if needed.
   * @return and optional describing the results of the search.
   */
  public Optional<CountryInfo> findCountry(String countrySpec) {
    Optional<CountryInfo> countryInfo = Optional.ofNullable(countriesCache.get(countrySpec));
    if (countryInfo.isEmpty()) {
      countryInfo = countriesCache.values().stream().filter(c -> c.getName().equalsIgnoreCase(countrySpec)).findAny();
    }
    return countryInfo;
  }

  /**
   * Find the border countries for the given country, if any.
   *
   * @param countryInfo
   * @return
   */
  public List<CountryInfo> getBorderCountries(CountryInfo countryInfo) throws Exception {


    List<String> borders = countryInfo.getBorders();
    return borders.stream().map(borderCountry -> countriesCache.get(borderCountry))
        .filter(Objects::nonNull).collect(Collectors.toList());
  }


  /**
   * Find the border countries for the given alpha3code, if any.
   *
   * @param alpha3Code
   * @return
   */
  public List<CountryInfo> getBorderCountries(String alpha3Code) throws Exception {

    Optional<CountryInfo> foundCountry = Optional.ofNullable(countriesCache.get(alpha3Code));
    if (foundCountry.isEmpty()) {
      throw new Exception("could not find country " + alpha3Code);
    }
    List<String> borders = foundCountry.get().getBorders();
    return borders.stream().map(borderCountry -> countriesCache.get(borderCountry))
        .filter(Objects::nonNull).collect(Collectors.toList());
  }

}
