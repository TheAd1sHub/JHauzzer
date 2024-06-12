package com.skilkihodin.jhauzzer.controller.api;

import com.skilkihodin.jhauzzer.model.accounts.Account;
import com.skilkihodin.jhauzzer.controller.repo.AccountsRepo;
import com.skilkihodin.jhauzzer.model.accounts.RawLoginData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1/accounts")
public final class AccountsApiController {

    @Autowired private AccountsRepo accountsRepo;

    @GetMapping(value = "/")
    @ResponseBody
    public String getPage() {
        return "Main accounts page.\nTry to specify your request with sub-address.";
    }

    @GetMapping(value = "/find")
    public String displayAccountSearch() {
        return "user-search-page.html";
    }

    @GetMapping(value = "/find/{login}")
    @ResponseBody
    public Account getAccount(@PathVariable("login") String login) {

        return accountsRepo
                .findById(login)
                .orElse(null);
    }

    @PostMapping("/new")
    @ResponseBody
    public String addAccount(@RequestBody RawLoginData loginData) {

        if (accountsRepo.existsById(loginData.getLogin())) {
            throw new UnsupportedOperationException("Account with such login is already registered.");
        }

        accountsRepo.save(Account.fromRawLoginData(loginData));

        return "Account created successfully.";
    }

    @PutMapping("/update-info")
    @ResponseBody
    public String updateAccountData(@RequestBody RawLoginData loginData) {

        if (!accountsRepo.existsById(loginData.getLogin())) {
            throw new UnsupportedOperationException("The given account does not exist or the credentials provided are incorrect.");
        }

        Account updatedAccount = Account.fromRawLoginData(loginData);

        accountsRepo.save(updatedAccount);

        return "Data updated successfully.";
    }

    @DeleteMapping("/remove")
    @ResponseBody
    public String deleteAccount(@RequestBody RawLoginData loginData) {

        if (!accountsRepo.existsById(loginData.getLogin())) {

            throw new UnsupportedOperationException("The given account does not exist.");
        }

        Account accountForDeletion = accountsRepo.getReferenceById(loginData.getLogin());
        Account providedCredentials = Account.fromRawLoginData(loginData);

        if (accountForDeletion.getType() != providedCredentials.getType()
           || accountForDeletion.getPasswordHash() != providedCredentials.getPasswordHash()) {

            throw new IllegalArgumentException("The credentials provided are incorrect.");
        }

        accountsRepo.delete(accountsRepo.getReferenceById(loginData.getLogin()));

        return "Account deleted successfully.";
    }
}
