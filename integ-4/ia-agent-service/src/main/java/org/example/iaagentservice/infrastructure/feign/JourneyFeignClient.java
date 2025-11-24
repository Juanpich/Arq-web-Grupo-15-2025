package org.example.iaagentservice.infrastructure.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name="journey-service",contextId = "journeyClient" ,url="http://localhost:8002/journey")
public interface JourneyFeignClient {

    @GetMapping("/execute-sql/{sqlQuery}")
    List<Object[]> executeSqlRequest(@PathVariable(name = "sqlQuery") String sqlQuery);
}
