package org.example.movementservice.infraestructure.feign;


import org.example.movementservice.domain.models.Account;
import org.example.movementservice.domain.models.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@FeignClient(name="user-service",contextId = "accountClient" ,url="http://localhost:8001/account")
public interface AccountFeingClient {

    @GetMapping("/{id}")
    Account getAccountsById(@PathVariable(name = "id") Long id);

    @GetMapping("/users/{id}")
    List<Account> getAccountsByUserId(@PathVariable(name = "id") Long accountId);

}
