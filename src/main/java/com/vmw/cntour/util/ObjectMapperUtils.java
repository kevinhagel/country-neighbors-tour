package com.vmw.cntour.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.vavr.control.Try;

/**
 * ObjectMapperUtils
 *
 * @author Kevin Hagel
 * @since 2021-04-02
 */
public class ObjectMapperUtils {

  public static String prettyPrintOrElse(Object object) {
    return Try.of(() -> new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(object))
        .getOrElseTry(() -> new ObjectMapper().writeValueAsString(object));
  }

  public static String prettyPrint(Object object) {
    return Try.of(() -> new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(object))
        .getOrElse("unable to write object " + object);
  }

  public static String toString(Object object) {
    return Try.of(() -> new ObjectMapper().writeValueAsString(object))
        .getOrElse("{}");

  }


}
