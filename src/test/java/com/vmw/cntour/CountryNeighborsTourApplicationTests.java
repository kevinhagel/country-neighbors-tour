package com.vmw.cntour;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

@SpringBootTest
class CountryNeighborsTourApplicationTests {

  @Test
  void contextLoads() {
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
