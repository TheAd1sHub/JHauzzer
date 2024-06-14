package com.skilkihodin.jhauzzer.service;

import com.skilkihodin.jhauzzer.controller.repo.AccountsRepo;
import com.skilkihodin.jhauzzer.model.accounts.Account;
import com.skilkihodin.jhauzzer.model.accounts.RawLoginData;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountsService implements UserDetailsService {

    @Autowired
    private AccountsRepo accountsRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> userAccount = accountsRepo.findById(username);
        return userAccount.orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
    }

    public void addAccount(RawLoginData loginData) {
        addAccount(Account.fromRawLoginData(loginData));
    }

    public void addAccount(Account account) throws UnsupportedOperationException {
        if (accountsRepo.existsById(account.getLogin())) {
            throw new UnsupportedOperationException("Account with such login is already registered.");
        }

        System.out.println(account);

        accountsRepo.save(account);
    }

    public void updateAccount(String login, RawLoginData loginData) throws UsernameNotFoundException {
        updateAccount(login, Account.fromRawLoginData(loginData));
    }

    public void updateAccount(String login, Account account) throws UsernameNotFoundException {
        if (!accountsRepo.existsById(login)) {
            throw new UsernameNotFoundException(login + " not found");
        }

        if (!Objects.equals(account.getUsername(), login)) {
            deleteAccount(login);
        }

        addAccount(account);
    }

    public Account getAccount(String login) throws UsernameNotFoundException {

        if (!accountsRepo.existsById(login)) {
            throw new UsernameNotFoundException(login + " not found");
        }

        return accountsRepo.getReferenceById(login);
    }

    public void deleteAccount(String login) throws UsernameNotFoundException {

        if (!accountsRepo.existsById(login)) {
            throw new UsernameNotFoundException(login + " not found");
        }

        accountsRepo.delete(accountsRepo.getReferenceById(login));
    }

}
