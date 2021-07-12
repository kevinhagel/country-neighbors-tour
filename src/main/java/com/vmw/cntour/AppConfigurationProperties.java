package com.vmw.cntour;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * AppConfigurationProperties
 *
 * @author Kevin Hagel
 * @since 2021-07-12
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "app")
public class AppConfigurationProperties {

  private String rapidApiKey;
  private String rapidApiHost;
  private String rapidApiUrl;

  private String exchangeRatesUrl;
  private String exchangeRatesAPIAccessKey;

}
