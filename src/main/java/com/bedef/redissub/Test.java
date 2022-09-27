package com.bedef.redissub;

import com.bedef.redissub.domain.PersonInfo;
import com.bedef.redissub.feign.PersonInfoClient;
import feign.Feign;
import feign.jackson.JacksonEncoder;

public class Test {

    public static void main(String[] args){
        //All status codes other than 2XX will trigger an error
        // custom error decoder possible: https://github.com/OpenFeign/feign#error-handling
        PersonInfoClient client = Feign.builder()
                .encoder(new JacksonEncoder())
                .target(PersonInfoClient.class, "http://localhost:8080");

        client.insertInfo(PersonInfo.builder()
                        .name("TC")
                        .unit("29Bn Log")
                        .milCode("0700290")
                .age(33)
                .build()
        );
    }
}
