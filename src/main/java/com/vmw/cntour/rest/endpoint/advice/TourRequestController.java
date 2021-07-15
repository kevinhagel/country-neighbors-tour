package com.vmw.cntour.rest.endpoint.advice;

import com.vmw.cntour.model.TourRequest;
import com.vmw.cntour.model.TourResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.reactive.function.client.WebClient;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * TourRequestController
 *
 * @author Kevin Hagel
 * @since 2021-07-15
 */
@Controller
@Slf4j
public class TourRequestController {

  @GetMapping("/tourRequest")
  public String tourRequestForm(@ModelAttribute("tourRequest") TourRequest tourRequest,
      BindingResult bindingResult, HttpServletRequest request, Model model
  ) {
    if (bindingResult.hasErrors()) {
      System.out.println("There was a error " + bindingResult);

      return "/";
    }
    model.addAttribute("tourRequest", TourRequest.builder().build());
    return "/";
  }

  @PostMapping("/tourRequest")
  public String tourRequestSubmit(@ModelAttribute TourRequest tourRequest,
      @ModelAttribute("tourResponse") TourResponse tourResponse, BindingResult bindingResult, Model model,
      HttpSession session) {
    model.addAttribute("tourRequest", tourRequest);

     tourResponse = WebClient
        .builder()
        .baseUrl("http://localhost:8080/api/v1/requestTour")
        .build()
        .post()
        .bodyValue(tourRequest)
        .retrieve()
        .bodyToMono(TourResponse.class)
        .doOnError(throwable -> System.out.println("error " + throwable))
        .block();

     session.setAttribute("tourResponse", tourResponse);

    model.addAttribute("tourResponse", tourResponse);
    return "redirect:/";
  }
}

