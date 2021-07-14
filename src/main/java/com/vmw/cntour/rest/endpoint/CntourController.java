package com.vmw.cntour.rest.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vmw.cntour.model.TourRequest;
import com.vmw.cntour.model.TourResponse;
import com.vmw.cntour.rest.service.TourService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.*;

/**
 * CntourController
 *
 * @author Kevin Hagel
 * @since 2021-07-12
 */
@RestController
@RequestMapping("/api/v1")
@Slf4j
public class CntourController {
  @Autowired
  private TourService tourService;

  @Autowired
  private ObjectMapper objectMapper;

  /**
   * User is requesting a tour.
   *
   * @param tourRequest the request arriving from the client.
   * @return a tour response.
   */
  @PostMapping("/requestTour")
  public TourResponse requestTour(@RequestBody TourRequest tourRequest) throws Exception {
    log.info("tour request {}", tourRequest);
    return tourService.buildTour(tourRequest);
  }

  @SneakyThrows
  @GetMapping("/oidc-principal")
  public String  getOidcUserPrincipal(
      @AuthenticationPrincipal OidcUser principal) {
    return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(principal);
  }

}
