package com.vmw.cntour.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * ExchangeRates the result of the "latest" exchange rates compared to the EUR.
 *
 * @author Kevin Hagel
 * @since 2021-07-12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExchangeRates {

  private boolean success;
  private String timestamp;
  private String base;
  private String date;
  private List<Rate> rates = new ArrayList<>();

  public void setRates(JsonNode jsonNode) throws JsonProcessingException {
    System.out.println("jsonNode = " + jsonNode);

  }

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class Rate {
    private String alpha3Code;
    private BigDecimal euroConversion;
  }


}
