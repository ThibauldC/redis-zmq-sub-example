package com.bedef.redissub.feign;

import com.bedef.redissub.domain.PersonInfo;
import feign.Headers;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient
public interface PersonInfoClient {

    @RequestLine("POST /tasks/insert")
    @Headers("Content-Type: application/json")
    void insertInfo(PersonInfo info);
}
