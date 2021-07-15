package com.vmw.cntour.model;

import lombok.Builder;
import lombok.Getter;

/**
 * StateItem
 *
 * @author Kevin Hagel
 * @since 2021-07-14
 */
@Getter
@Builder
public class StateItem {

  private State id;
  private String text;
  private String slug;

}

