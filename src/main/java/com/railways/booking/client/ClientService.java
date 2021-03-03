package com.railways.booking.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "Search-client",url="127.0.0.1:8083")
public interface ClientService {
    @RequestMapping(method = RequestMethod.GET,path = "/search")
    List<Long> getTrainIds(@RequestParam("fromLocation") String fromLocation,@RequestParam("toLocation") String toLocation);
}
