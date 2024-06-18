package com.skilkihodin.jhauzzer.controller.api;

import com.skilkihodin.jhauzzer.model.accounts.Account;
import com.skilkihodin.dto.RawLoginData;
import com.skilkihodin.jhauzzer.service.AccountsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/v1/accounts")
public final class AccountsApiController {

    @Autowired
    private AccountsService accountsService;

    @GetMapping(value = "/")
    @ResponseBody
    public String getPage() {
        return "Main accounts page.\nTry to specify your request with sub-address.";
    }

    @GetMapping(value = "/find")
    public String displayAccountSearch() {
        return "user-search-page.html";
    }

    @GetMapping(value = "/get/{id}")
    @ResponseBody
    public Account getAccount(@PathVariable("id") Long id) {

        return accountsService.get(id);
    }

    @PostMapping("/register")
    @ResponseBody
    public String addAccount(@RequestBody RawLoginData loginData) {
        accountsService.add(loginData);

        return "Account created successfully.";
    }

    @PutMapping("/update/{id}")
    @ResponseBody
    public String updateAccountData(@PathVariable Long id, @RequestBody RawLoginData loginData) {
        accountsService.update(id, loginData);

        return "Data updated successfully.";
    }

    @DeleteMapping("/delete/{id}")
    @ResponseBody
    public String deleteAccount(@PathVariable Long id) {

        accountsService.delete(id);

        return "Account deleted successfully.";
    }

    @GetMapping("/get-all")
    @ResponseBody
    public List<Account> getAll() {
        return accountsService.getAll();
    }

}