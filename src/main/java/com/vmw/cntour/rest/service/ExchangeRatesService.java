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

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;

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

  private void setExchangeRatesCache(ExchangeRates exchangeRatesCache) {
    this.exchangeRatesCache = exchangeRatesCache;
  }

  private ExchangeRates exchangeRatesCache;


  @PostConstruct
  public void init() throws Exception {
    exchangeRatesClient
        .get()
        .uri(uriBuilder -> uriBuilder
            .path("/latest")
            .queryParam("access_key", appConfigurationProperties.getExchangeRatesAPIAccessKey())
            .build())
        .retrieve()
        .bodyToMono(ExchangeRates.class)
        .doOnSuccess(success -> System.out.println("success = " + success))
        .subscribe(this::setExchangeRatesCache)
    ;
//    exchangeRatesCache = objectMapper.readValue(exchangeRatesJson.getInputStream(), ExchangeRates.class);

  }


  /**
   * Perform the conversion.  My subscription does not include access to their exchange endpoint, so I have to get them
   * all, do the math myself.
   *
   * @param from
   * @param to
   * @param amount
   * @return
   */
  public BigDecimal exchangeConversion(String from, String to, BigDecimal amount) {

    BigDecimal fromRate = exchangeRatesCache.getRates().getOrDefault(from, amount);
    BigDecimal toRate = exchangeRatesCache.getRates().getOrDefault(to, amount);

    BigDecimal result = amount
        .multiply(toRate, MathContext.DECIMAL32)
        .divide(fromRate, 4, RoundingMode.HALF_UP);

    return result;
  }

}
