package com.vmw.cntour.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * CountryInfo contains the minimum information I need for this task.
 *
 * @author Kevin Hagel
 * @since 2021-07-12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CountryInfo {

  private String name;
  private String alpha3Code;
  private List<String> borders;
  private List<String> currencies;

}
