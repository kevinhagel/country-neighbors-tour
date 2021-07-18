package com.vmw.cntour.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.Executors;

/**
 * WebConfig
 *
 * @author Kevin Hagel
 * @since 2021-07-14
 */
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {


  @Override
  public void addResourceHandlers(ResourceHandlerRegistry registry) {
    registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
    registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
  }

  @Override
  public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
    configurer.setTaskExecutor(getTaskExecutor());
  }

  @Bean
  protected ConcurrentTaskExecutor getTaskExecutor() {
    return new ConcurrentTaskExecutor(Executors.newFixedThreadPool(5));
  }
}
