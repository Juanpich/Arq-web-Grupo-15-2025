package org.example.iaagentservice.infrastructure.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="journey-service",contextId = "journeyClient" ,url="http://localhost:8002/journey")
public interface JourneyFeignClient {

    @PostMapping("/execute-sql")
    List<Object[]> executeSqlRequest(@RequestBody String sqlQuery);
}
