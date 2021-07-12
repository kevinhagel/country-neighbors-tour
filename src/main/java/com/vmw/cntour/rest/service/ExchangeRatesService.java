package com.vmw.cntour.rest.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vmw.cntour.AppConfigurationProperties;
import com.vmw.cntour.model.ExchangeRates;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

/**
 * ExchangeRatesService provides access to the exchange rates data, performs exchanges.
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
  private AppConfigurationProperties appConfigurationProperties;




  /**
   * Perform the conversion.  My subscription does not include access to their exchange endpoint, so I have to get them
   * all, do the math myself.
   *
   * @param from
   * @param to
   * @param amount
   * @return
   */
  public Mono<ExchangeRates> exchangeConversion(String from, String to, BigDecimal amount) {
    Mono<ExchangeRates> result = exchangeRatesClient
        .get()
        .uri(uriBuilder -> uriBuilder
            .path("/latest")
            .queryParam("access_key", appConfigurationProperties.getExchangeRatesAPIAccessKey())
            .build())
        .retrieve()
        .bodyToMono(ExchangeRates.class);


    return result;
  }

}
