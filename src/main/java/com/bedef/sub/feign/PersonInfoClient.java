package com.bedef.sub.feign;

import com.bedef.sub.domain.PersonInfo;
import feign.Headers;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient
public interface PersonInfoClient {

    @RequestLine("POST /tasks/insert")
    @Headers("Content-Type: application/json")
    void insertInfo(PersonInfo info);
}
