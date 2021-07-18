package com.vmw.cntour.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Select2Results
 *
 * @author Kevin Hagel
 * @since 2021-07-18
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Select2Results {

  private List<Result> results;

  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class Result {
    private String id;
    private String text;
  }
}
