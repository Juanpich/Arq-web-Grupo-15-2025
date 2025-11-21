package org.example.gateway.infraestructure.feign;



import org.example.gateway.entity.User;
import org.example.gateway.service.dto.user.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

@FeignClient(name= "user-service", url="http://localhost:8001/users")
public interface UserFeignClient {
    @GetMapping("/email/{email}")
    Optional<User> findByEmail(@PathVariable("email") String email);
    @PostMapping()
    User save(@RequestBody UserDTO user);

}
