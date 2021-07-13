package com.vmw.cntour.rest.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vmw.cntour.AppConfigurationProperties;
import com.vmw.cntour.model.ExchangeRates;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.scheduler.Schedulers;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 * ExchangeRatesService provides access to the exchange rates data, performs exchanges.  For this to work as a simple
 * task example, this class memoizes the results, makes a web client request the first time it is run to get the
 * results, caches them, and uses them after that.
 *
 * @author Kevin Hagel
 * @since 2021-07-12
 */
@Service
@Slf4j
public class ExchangeRatesService {

  @Autowired
  private WebClient exchangeRatesClient;

  @Autowired
  private ObjectMapper objectMapper;

  @Autowired
  private AppConfigurationProperties appConfigurationProperties;

  @Value("classpath:exchange-rates.json")
  private Resource exchangeRatesJson;


  private ExchangeRates exchangeRatesCache;


  /**
   * After injection execute the webclient in a boundedElastic thread to load the exchange rates into our cache.
   */
  @PostConstruct
  public void init() {
    exchangeRatesClient
        .get()
        .uri(uriBuilder -> uriBuilder
            .path("/latest")
            .queryParam("access_key", appConfigurationProperties.getExchangeRatesAPIAccessKey())
            .build())
        .retrieve()
        .bodyToMono(ExchangeRates.class)
        .doOnError(throwable -> log.error("error loading exchange rates", throwable))
        .subscribeOn(Schedulers.boundedElastic())
        .doOnSuccess(exchangeRates -> log.info("Completed loading {} exchange rates ", exchangeRates.getRates().size()))
        .subscribe(this::setExchangeRatesCache);
  }

  /**
   * Used in the webclient load to set the exchangeRatesCache
   *
   * @param exchangeRatesCache the value to set in the cache.
   */
  private void setExchangeRatesCache(ExchangeRates exchangeRatesCache) {
    this.exchangeRatesCache = exchangeRatesCache;
  }

  /**
   * Perform the conversion.  My subscription does not include access to their exchange endpoint, so I have to get them
   * all, do the math myself.
   *
   * @param from   the original currency
   * @param to     the destination currency.
   * @param amount the amount to convert.
   * @return the converted result.
   */
  public BigDecimal exchangeConversion(String from, String to, BigDecimal amount) {
    BigDecimal fromRate = exchangeRatesCache.getRates().getOrDefault(from, amount);
    BigDecimal toRate = exchangeRatesCache.getRates().getOrDefault(to, amount);
    return amount
        .multiply(toRate, MathContext.DECIMAL32)
        .divide(fromRate, 4, RoundingMode.HALF_UP);
  }

}
