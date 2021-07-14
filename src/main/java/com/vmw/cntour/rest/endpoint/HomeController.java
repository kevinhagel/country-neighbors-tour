package com.vmw.cntour.rest.endpoint;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * HomeController for non rest api urls.  Note that it is a @Controller and not a @RestController
 *
 * @author Kevin Hagel
 * @since 2021-07-14
 */
@Controller
@RequestMapping("/")
@Slf4j
public class HomeController {


  @Value("${spring.application.name}")
  String appName;

  @GetMapping("/")
  public String homePage(Model model) {
    model.addAttribute("appName", appName);
    return "home";
  }

  @GetMapping("/oidc-principal")
  public OidcUser getOidcUserPrincipal(
      @AuthenticationPrincipal OidcUser principal) {
    return principal;
  }
}
