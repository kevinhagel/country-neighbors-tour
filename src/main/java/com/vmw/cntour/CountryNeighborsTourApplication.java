package com.vmw.cntour;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
//@EnableWebFlux
@SpringBootApplication
public class CountryNeighborsTourApplication {


  public static void main(String[] args) {
    SpringApplication.run(CountryNeighborsTourApplication.class, args);
  }


}
