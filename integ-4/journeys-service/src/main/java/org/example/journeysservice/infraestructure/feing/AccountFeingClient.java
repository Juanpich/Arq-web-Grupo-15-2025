package org.example.journeysservice.infraestructure.feing;

import org.example.journeysservice.models.Account;
import org.example.journeysservice.models.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;


@FeignClient(name="user-service",contextId = "accountClient" ,url="http://localhost:8001/account")
public interface AccountFeingClient {

    @GetMapping("/user/{id}")
    List<Account> getAccountsByUser(@PathVariable(name = "id") Long userId);

    @GetMapping("/{id}/users")
    List<User> getUsersByAccountId(@PathVariable(name = "id") Long accountId);

}
