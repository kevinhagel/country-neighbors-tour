package com.vmw.cntour.rest.endpoint;

import com.vmw.cntour.model.State;
import com.vmw.cntour.model.StateItem;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * StatesController
 *
 * @author Kevin Hagel
 * @since 2021-07-14
 */
@RestController
@RequestMapping("/states")
public class StatesRestController {

  @GetMapping
  @ResponseBody
  public List<StateItem> stateItems(@RequestParam(value = "q", required = false) String query) {
    if (StringUtils.isEmpty(query)) {
      return Arrays.stream(State.values())
          .limit(15)
          .map(this::mapToStateItem)
          .collect(Collectors.toList());
    }

    return Arrays.stream(State.values())
        .filter(state -> state.getLabel()
            .toLowerCase()
            .contains(query))
        .limit(15)
        .map(this::mapToStateItem)
        .collect(Collectors.toList());
  }

  private StateItem mapToStateItem(State state) {
    return StateItem.builder()
        .id(state)
        .text(state.getLabel())
        .slug(state.name())
        .build();
  }
}
