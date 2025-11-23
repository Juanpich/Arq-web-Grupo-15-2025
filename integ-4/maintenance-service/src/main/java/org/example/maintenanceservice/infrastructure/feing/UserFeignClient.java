package org.example.maintenanceservice.infrastructure.feing;

import org.example.maintenanceservice.domain.models.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "user-service", url = "http://localhost:8001/users")
public interface UserFeignClient {

    @GetMapping("/{id}")
    User getUserById(@PathVariable Long userId);
}
