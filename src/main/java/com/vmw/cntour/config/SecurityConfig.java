package com.vmw.cntour.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import java.net.URI;

/**
 * SecurityConfig
 *
 * @author Kevin Hagel
 * @since 2021-07-14
 */
@EnableWebSecurity(debug = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private ClientRegistrationRepository clientRegistrationRepository;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
////    super.configure(http);
//    http
//        .csrf().disable()
//        .authorizeRequests()
//        .mvcMatchers(HttpMethod.POST, "/api/v1/**")
////        .authenticated()
////        .permitAll()
//
//
//    ;
//    http
//        .csrf().disable()
//        .authorizeRequests(authorizeRequests -> authorizeRequests
//            .mvcMatchers("/api/v1/**")
//            .permitAll()
//            .anyRequest().authenticated()
//            .oauth2Login(oauthLogin -> oauthLogin.permitAll())
//        );

    http
        .csrf().disable()
        .authorizeRequests(authorizeRequests -> authorizeRequests
            .mvcMatchers("/api/v1/**").permitAll()
            .anyRequest().authenticated())
        .oauth2Login(oauthLogin -> oauthLogin.permitAll())
        .logout(logout -> logout
            .logoutSuccessHandler(oidcLogoutSuccessHandler()));

  }

  private LogoutSuccessHandler oidcLogoutSuccessHandler() {
    OidcClientInitiatedLogoutSuccessHandler oidcLogoutSuccessHandler =
        new OidcClientInitiatedLogoutSuccessHandler(
            this.clientRegistrationRepository);

    oidcLogoutSuccessHandler.setPostLogoutRedirectUri(
        URI.create("http://localhost:8080/home"));

    return oidcLogoutSuccessHandler;
  }
}
