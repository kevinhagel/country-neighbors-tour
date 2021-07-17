package com.vmw.cntour.rest.endpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vmw.cntour.model.CountryInfo;
import com.vmw.cntour.model.TourRequest;
import com.vmw.cntour.model.TourResponse;
import com.vmw.cntour.rest.service.CountriesService;
import com.vmw.cntour.rest.service.TourService;
import io.swagger.v3.oas.annotations.headers.Header;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

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
  private CountriesService countriesService;


  @Autowired
  private ObjectMapper objectMapper;

  /**
   * User is requesting a tour.
   *
   * @param tourRequest the request arriving from the client.
   * @return a tour response.
   */
  @PostMapping(value = "/requestTour")
  @CrossOrigin(origins = {"*"})
  public TourResponse requestTour(@RequestBody TourRequest tourRequest) throws Exception {
    log.info("tour request {}", tourRequest);
    TourResponse tourResponse = tourService.buildTour(tourRequest);
    log.info("tourResponse = " + tourResponse);
    return tourResponse;
//    return ResponseEntity.ok(tourResponse);
  }

  @SneakyThrows
  @GetMapping("/oidc-principal")
  @CrossOrigin
  public String getOidcUserPrincipal(
      @AuthenticationPrincipal OidcUser principal) {
    return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(principal);
  }

  @GetMapping("/countries")
  @CrossOrigin
  public List<CountryInfo> countries(@RequestParam(value = "term", required = false, defaultValue = "") String term) {
    System.out.println("term = " + term);
    return countriesService.findAll()
        .stream()
        .filter(countryInfo -> StringUtils.startsWithIgnoreCase(countryInfo.getName(), term))
        .peek(c-> System.out.println(c))
        .collect(Collectors.toList());
  }


}
