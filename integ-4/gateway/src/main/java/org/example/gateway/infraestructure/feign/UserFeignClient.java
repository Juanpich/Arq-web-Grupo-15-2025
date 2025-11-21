package org.example.gateway.infraestructure.feign;


import org.example.gateway.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name= "user-service", url="http://localhost:8001/users")
public interface UserFeignClient {
    @GetMapping("/email/{email}")
    User findByEmail(@PathVariable("email") String email);
}
