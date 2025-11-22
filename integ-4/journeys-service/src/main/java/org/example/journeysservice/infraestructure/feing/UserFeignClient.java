package org.example.journeysservice.infraestructure.feing;

import org.example.journeysservice.models.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name="user-service", url="http://localhost:8001/users")
public interface UserFeignClient {
    @GetMapping("/{id}")
    User getUser( @PathVariable Long id);
}
