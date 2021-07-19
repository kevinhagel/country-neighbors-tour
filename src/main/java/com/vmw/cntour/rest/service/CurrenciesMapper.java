package com.vmw.cntour.rest.service;

import com.vmw.cntour.model.CurrencyInfo;
import com.vmw.cntour.util.ObjectMapperUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * CurrenciesMapper
 *
 * @author Kevin Hagel
 * @since 2021-07-18
 */
@Component
@Slf4j
public class CurrenciesMapper {

  private final Map<String, CurrencyInfo> currenciesMap = new TreeMap<>();

  private Resource currenciesResource;

  @PostConstruct
  public void init() throws IOException {
    load();
  }

  public void load() throws IOException {
    load(currenciesResource);
  }

  public Collection<CurrencyInfo> values() {
    return currenciesMap.values();
  }

  /**
   * given an ISO Currency, return the currency info.
   * @param isoCurrency the iso currency
   * @return the currency info, in an optional.
   */
  public Optional<CurrencyInfo> find(String isoCurrency) {
    return Optional.ofNullable(currenciesMap.get(isoCurrency));
  }

  private void load(Resource resource) {
    log.info("loading currencies from {}", resource);
    try (InputStream is = resource.getInputStream(); Scanner scanner = new Scanner(is)) {
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        String[] split = line.split("\t");
        String isoCode = split[0];
        String currency = split[1];
        String country = split[2];
        currenciesMap.put(isoCode, CurrencyInfo
            .builder()
            .isoCode(isoCode)
            .currency(currency)
            .country(country)
            .build());
      }
    } catch (Exception e) {
      log.error("error attempting to load {}", currenciesMap, e);
    }
    log.info("loaded {} currencies from currencies names", currenciesMap.size());
    log.info("\nloaded currenciesMap:\n{}", ObjectMapperUtils.prettyPrint(currenciesMap));
  }

  public CurrencyInfo get(String isoCode) {
    return Optional.ofNullable(currenciesMap.get(isoCode)).orElseGet(() -> currenciesMap.get("EUR"));
  }

  /**
   * Set the spread sheet as a Resource.
   *
   * @param currenciesResource the parameter to set.
   */
  @Value("${app.currenciesNamesResource:classpath:/currencies-names.csv}")
  public void setCurrenciesResource(Resource currenciesResource) {
    this.currenciesResource = currenciesResource;
  }


}
