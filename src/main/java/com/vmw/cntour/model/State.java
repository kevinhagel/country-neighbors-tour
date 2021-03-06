package com.vmw.cntour.model;

import lombok.Data;
import lombok.Getter;

/**
 * State
 *
 * @author Kevin Hagel
 * @since 2021-07-14
 */
public enum State {

  AL("Alabama"),
  AK("Alaska"),
  AZ("Arizona"),
  AR("Arkansas"),
  CA("California"),
  CO("Colorado"),
  CT("Connecticut"),
  DE("Delaware"),
  DC("District of Columbia"),
  FL("Florida"),
  GA("Georgia"),
  HI("Hawaii"),
  ID("Idaho"),
  IL("Illinois"),
  IN("Indiana"),
  IA("Iowa"),
  KS("Kansas"),
  KY("Kentucky"),
  LA("Louisiana"),
  ME("Maine"),
  MT("Montana"),
  NE("Nebraska"),
  NV("Nevada"),
  NH("New Hampshire"),
  NJ("New Jersey"),
  NM("New Mexico"),
  NY("New York"),
  NC("North Carolina"),
  ND("North Dakota"),
  OH("Ohio"),
  OK("Oklahoma"),
  OR("Oregon"),
  MD("Maryland"),
  MA("Massachusetts"),
  MI("Michigan"),
  MN("Minnesota"),
  MS("Mississippi"),
  MO("Missouri"),
  PA("Pennsylvania"),
  RI("Rhode Island"),
  SC("South Carolina"),
  SD("South Dakota"),
  TN("Tennessee"),
  TX("Texas"),
  UT("Utah"),
  VT("Vermont"),
  VA("Virginia"),
  WA("Washington"),
  WV("West Virginia"),
  WI("Wisconsin"),
  WY("Wyoming");

  @Getter
  String label;

  State(String label) {
    this.label = label;
  }
}