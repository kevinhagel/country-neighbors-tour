package com.vmw.cntour;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vmw.cntour.model.CountryInfo;
import com.vmw.cntour.model.ExchangeRates;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.core.io.Resource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.util.List;

@JsonTest
class CountryNeighborsTourApplicationTests {

  @Autowired
  private ObjectMapper objectMapper;

  @Value("classpath:exchange-rates.json")
  private Resource exchangeRatesJson;

  @Value("classpath:rest-countries.json")
  private Resource restCountriesJson;


  @Test
  void contextLoads() throws Exception {
     String json = IOUtils.toString(exchangeRatesJson.getInputStream(), StandardCharsets.UTF_8);
    ExchangeRates exchangeRates = objectMapper.readValue(json.getBytes(StandardCharsets.UTF_8), ExchangeRates.class);
    System.out.println("exchangeRates = " + exchangeRates);

    json = IOUtils.toString(restCountriesJson.getInputStream(), StandardCharsets.UTF_8);
    TypeReference<List<CountryInfo>> ref = new TypeReference<List<CountryInfo>>() {};

    List<CountryInfo> countries = objectMapper.readValue(json, ref);
    System.out.println("coutnries = " + countries);

  }

  @Test
  public void test_calculations() throws Exception {
    BigDecimal budgetPerCountry = BigDecimal.valueOf(100.00);
    BigDecimal totalBudget = BigDecimal.valueOf(1200.00);
    int numberCountries = 5;

    BigDecimal oneTourCost = budgetPerCountry.multiply(BigDecimal.valueOf(numberCountries));
    System.out.println("oneTourCost = " + oneTourCost);

    BigInteger numberTours = totalBudget.divide(oneTourCost, 0, RoundingMode.DOWN).toBigInteger();
    System.out.println("numberTours = " + numberTours);

  }

}
