package com.vmw.cntour.config;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * SecurityConfig
 *
 * @author Kevin Hagel
 * @since 2021-07-14
 */
@EnableWebSecurity(debug = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  @Override
  protected void configure(HttpSecurity http) throws Exception {
//    super.configure(http);
    http
        .csrf().disable()
        .authorizeRequests()
        .mvcMatchers(HttpMethod.POST, "/api/v1/**")
        .permitAll()


    ;


  }
}
