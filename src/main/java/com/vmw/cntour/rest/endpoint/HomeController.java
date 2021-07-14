package com.vmw.cntour.rest.endpoint;

import com.vmw.cntour.model.TourRequest;
import com.vmw.cntour.model.TourResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;

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

    TourRequest request = TourRequest
        .builder()
        .budgetPerCountry(BigDecimal.valueOf(100.00))
        .totalBudget(BigDecimal.valueOf(1200.00))
        .currency("EUR")
        .startingCountry("BGR")
        .build();
//    RestTemplate restTemplate = new RestTemplate();
//    TourResponse o = restTemplate.postForObject("http://localhost:8080/api/v1/requestTour", request, TourResponse.class);
//    System.out.println("o =  " + o);
//    model.addAttribute("tourResponse", o);

    WebClient
        .builder()
        .baseUrl("http://localhost:8080/api/v1/requestTour")
        .build()
        .post()
        .bodyValue(request)
        .retrieve()
        .bodyToMono(String.class)
        .map(string
            -> "Retrieved using Client Credentials Grant Type: " + string)
        .subscribe(d -> log.info("d = " + d));

    return "home";
  }


}
