package com.skilkihodin.jhauzzer.controller;

import com.skilkihodin.jhauzzer.model.accounts.Account;
import com.skilkihodin.jhauzzer.model.accounts.AccountsRepo;
import com.skilkihodin.jhauzzer.model.accounts.RawLoginData;
import com.skilkihodin.jhauzzer.security.Crc32HashGenerator;
import com.skilkihodin.jhauzzer.security.Crc32SaltyHashGenerator;
import com.skilkihodin.jhauzzer.security.HashGenerator;
import com.skilkihodin.jhauzzer.service.AccountsService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
//@RequestMapping("/api/v1/account")
public final class ApiController {

    @Autowired private AccountsRepo accountsRepo;
    //@NonNull private final AccountsService accountsService;

    private final HashGenerator hashGenerator = new Crc32HashGenerator();

    @GetMapping(value = "/")
    public String getPage() {
        return "Hello!";
    }

    @GetMapping(value = "/{login}")
    public Account getAccount(@PathVariable String login) {

        return accountsRepo
                .findById(login)
                .orElse(null);
    }

    @PostMapping("/new")
    public String addAccount(@RequestBody RawLoginData loginData) {

        if (accountsRepo.existsById(loginData.getLogin())) {
            throw new UnsupportedOperationException("Account with such login is already registered.");
        }

        accountsRepo.save(Account.fromRawLoginData(loginData));

        return "Account created successfully.";
    }

    @PutMapping("/update-info")
    public String updateAccountData(@RequestBody RawLoginData loginData) {

        if (!accountsRepo.existsById(loginData.getLogin())) {
            throw new UnsupportedOperationException("The given account does not exist or the credentials provided are incorrect.");
        }

        return "Data updated successfully";
    }

    @DeleteMapping("/remove")
    public String deleteAccount(@RequestBody RawLoginData loginData) {

        if (!accountsRepo.existsById(loginData.getLogin())) {
            throw new UnsupportedOperationException("The given account does not exist or the credentials provided are incorrect.");
        }

        return "Account deleted";
    }
}
