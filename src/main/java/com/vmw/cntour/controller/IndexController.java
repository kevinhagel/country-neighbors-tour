package com.vmw.cntour.controller;

import com.vmw.cntour.model.TourRequest;
import com.vmw.cntour.model.TourResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 * IndexController controller for index.html
 *
 * @author Kevin Hagel
 * @since 2021-07-17
 */
@Controller
@RequestMapping("/")
@Slf4j
public class IndexController {

  @GetMapping("/")
  public String homePage(HttpSession session) {

    return "index";
  }
}
