package com.bedef.redissub.application.flowable;

import com.bedef.redissub.feign.PersonInfoClient;
import feign.Feign;
import feign.jackson.JacksonEncoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfig {
    @Value("${flowable.url:http://localhost:8080}")
    String flowableUrl;

    //All status codes other than 2XX will trigger an error
    // custom error decoder possible: https://github.com/OpenFeign/feign#error-handling
    @Bean
    PersonInfoClient client(){
        return Feign.builder()
                .encoder(new JacksonEncoder())
                .target(PersonInfoClient.class, flowableUrl);
    }
}
