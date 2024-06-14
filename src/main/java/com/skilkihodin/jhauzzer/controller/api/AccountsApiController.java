package com.skilkihodin.jhauzzer.controller.api;

import com.skilkihodin.jhauzzer.model.accounts.Account;
import com.skilkihodin.jhauzzer.controller.repo.AccountsRepo;
import com.skilkihodin.jhauzzer.model.accounts.RawLoginData;
import com.skilkihodin.jhauzzer.service.AccountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Controller
@RequestMapping("/api/v1/accounts")
public final class AccountsApiController {

    //@Autowired private AccountsRepo accountsRepo;
    @Autowired private AccountsService accountsService;

    @GetMapping(value = "/")
    @ResponseBody
    public String getPage() {
        return "Main accounts page.\nTry to specify your request with sub-address.";
    }

    @GetMapping(value = "/find")
    //@PreAuthorize("hasAuthority('USER') || hasAuthority('VIP_USER')")
    public String displayAccountSearch() {
        return "user-search-page.html";
    }

    @GetMapping(value = "/find/{login}")
    //@PreAuthorize("hasAuthority('USER') || hasAuthority('VIP_USER')")
    @ResponseBody
    public Account getAccount(@PathVariable("login") String login) {

        return accountsService.getAccount(login);
    }

    @PostMapping("/new")
    @ResponseBody
    public String addAccount(@RequestBody RawLoginData loginData) {
        System.out.println(123);

        accountsService.addAccount(loginData);

        return "Account created successfully.";
    }

    @PutMapping("/update-info")
    //@PreAuthorize("hasAuthority('ADMIN')")
    @ResponseBody
    public String updateAccountData(@RequestBody RawLoginData loginData) {

        accountsService.updateAccount(loginData.getLogin(), loginData);

        return "Data updated successfully.";
    }

    @DeleteMapping("/remove")
    //@PreAuthorize("hasAuthority('ADMIN')")
    @ResponseBody
    public String deleteAccount(@RequestBody String login) {

        accountsService.deleteAccount(login);

        return "Account deleted successfully.";
    }
}
