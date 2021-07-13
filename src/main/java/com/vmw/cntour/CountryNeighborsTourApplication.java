package com.vmw.cntour;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.annotation.PostConstruct;

//@EnableWebMvc
@SpringBootApplication
public class CountryNeighborsTourApplication {


  public static void main(String[] args) {
    SpringApplication.run(CountryNeighborsTourApplication.class, args);
  }


}
