package com.vmw.cntour.controller;

import com.vmw.cntour.model.TourRequest;
import com.vmw.cntour.model.TourResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 * HomeController for non rest api urls.  Note that it is a @Controller and not a @RestController
 *
 * @author Kevin Hagel
 * @since 2021-07-14
 */
@Controller
@RequestMapping("/home")
@Slf4j
public class HomeController {


  @Value("${spring.application.name}")
  String appName;


  @GetMapping("/")
  public String homePage(HttpSession session,
      Model model,
      @ModelAttribute("tourRequest") TourRequest tourRequest,
      @ModelAttribute("tourResponse") TourResponse tourResponse,
      BindingResult bindingResult) {
    model.addAttribute("appName", appName);
    model.addAttribute("tourRequest", TourRequest.builder().build());

    Optional.ofNullable(session.getAttribute("tourResponse")).map(TourResponse.class::cast)
        .ifPresent(tourResponse1 -> model.addAttribute("tourResponse", tourResponse1));


    return "home";
  }


}
