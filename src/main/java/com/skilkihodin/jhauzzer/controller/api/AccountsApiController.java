package com.skilkihodin.jhauzzer.controller.api;

import com.skilkihodin.jhauzzer.model.accounts.Account;
import com.skilkihodin.jhauzzer.dto.RawLoginData;
import com.skilkihodin.jhauzzer.service.AccountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
public final class AccountsApiController {

    private final AccountsService accountsService;

    @Autowired
    public AccountsApiController(AccountsService accountsService) {
        this.accountsService = accountsService;
    }

    @GetMapping(value = "/")
    public String getPage() {
        return "Main accounts page.\nTry to specify your request with sub-address.";
    }

    @GetMapping(value = "/get/{id}")
    public Account getAccount(@PathVariable("id") Long id) {

        return accountsService.get(id);
    }

    @PostMapping("/register")
    @PreAuthorize("hasRole('ROLE_UNAUTHENTICATED')")
    public String addAccount(@RequestBody RawLoginData loginData) {
        accountsService.add(loginData);

        return "Account created successfully.";
    }

    @PutMapping("/update/{id}")
    public String updateAccountData(@PathVariable Long id, @RequestBody RawLoginData loginData) {
        accountsService.update(id, loginData);

        return "Data updated successfully.";
    }

    @DeleteMapping("/delete/{id}")
    public String deleteAccount(@PathVariable Long id) {

        accountsService.delete(id);

        return "Account deleted successfully.";
    }

    @GetMapping("/get-all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Account> getAll() {
        return accountsService.getAll();
    }

}