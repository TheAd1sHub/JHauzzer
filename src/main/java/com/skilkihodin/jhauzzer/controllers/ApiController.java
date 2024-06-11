package com.skilkihodin.jhauzzer.controllers;

import com.skilkihodin.jhauzzer.models.accounts.Account;
import com.skilkihodin.jhauzzer.models.accounts.AccountsRepo;
import com.skilkihodin.jhauzzer.models.accounts.RawLoginData;
import com.skilkihodin.jhauzzer.security.Crc32SaltyHashGenerator;
import com.skilkihodin.jhauzzer.security.HashGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public final class ApiController {

    private final String SALT_WORD = "Turboflex";

    @Autowired
    private AccountsRepo accountsRepo;
    private final HashGenerator hashGenerator = new Crc32SaltyHashGenerator(SALT_WORD);

    @GetMapping(value = "/")
    public String getPage() {
        return "Hello!";
    }

    @GetMapping(value = "/users")
    public List<Account> getAccounts() {
        return accountsRepo.findAll();
    }

    @PostMapping("/register-new")
    public String addAccount(@RequestBody RawLoginData loginData) {

        System.out.println(loginData.getLogin());
        System.out.println(loginData.getPassword());
        System.out.println(loginData.getAccountType());

        accountsRepo.save(Account.fromRawLoginData(loginData));

        return "Saved...";
    }
}
