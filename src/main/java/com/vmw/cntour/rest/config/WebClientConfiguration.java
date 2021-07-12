package com.vmw.cntour.rest.config;

import com.vmw.cntour.AppConfigurationProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * WebClientConfiguration configures the web clients for cntour.
 *
 * @author Kevin Hagel
 * @since 2021-07-12
 */
@Configuration
@Slf4j
public class WebClientConfiguration {


  private AppConfigurationProperties appConfigurationProperties;

  /**
   * configure the Webclient for the rapid countries api.
   *
   * @return Webclient for the rapid countries api
   */
  @Bean
  public WebClient rapidApiCountriesClient() {
    return WebClient
        .builder()
        .baseUrl(appConfigurationProperties.getRapidApiUrl())
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE, MediaType.ALL_VALUE)
        .defaultHeaders(httpHeaders -> {
          httpHeaders.set(HttpHeaders.ACCEPT_LANGUAGE, "en");
          httpHeaders.set("x-rapidapi-host", appConfigurationProperties.getRapidApiHost());
          httpHeaders.set("x-rapidapi-key", appConfigurationProperties.getRapidApiKey());
        })
        .build();
  }


  /**
   * configure the Webclient for the exchange rates api.
   *
   * @return Webclient for the rapid countries api
   */
  @Bean
  public WebClient exchangeRatesClient() {
    return WebClient
        .builder()
        .baseUrl(appConfigurationProperties.getExchangeRatesUrl())
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE, MediaType.ALL_VALUE)
        .defaultHeaders(httpHeaders -> {
          httpHeaders.set(HttpHeaders.ACCEPT_LANGUAGE, "en");
        })
        .build();
  }

  /**
   * Set the appConfigurationProperties.
   *
   * @param appConfigurationProperties the properties to set.
   */
  @Autowired
  public void setUbbConfigProperties(AppConfigurationProperties appConfigurationProperties) {
    this.appConfigurationProperties = appConfigurationProperties;
  }


}
