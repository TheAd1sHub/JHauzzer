package com.skilkihodin.jhauzzer.service;

import com.skilkihodin.jhauzzer.controller.repo.AccountsRepo;
import com.skilkihodin.jhauzzer.model.accounts.Account;
import com.skilkihodin.dto.RawLoginData;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public final class AccountsService implements UserDetailsService {

    @Autowired
    private AccountsRepo repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> userAccount = repository
                .findAll()
                .stream()
                .filter(acc -> acc.getUsername().equals(username))
                .findFirst();

        return userAccount.orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
    }

    public void add(RawLoginData loginData) {
        Account newAccount = Account.fromRawLoginData(loginData);
        newAccount.setId(repository.count() + 1);
        add(newAccount);
    }

    public void add(Account account) throws UnsupportedOperationException {
        if (repository.existsById(account.getId())) {
            throw new UnsupportedOperationException("Account under such ID is already registered.");
        }

        if (repository.getByLogin(account.getLogin()).isPresent()) {
            throw new UnsupportedOperationException("Account under such login is already registered.");
        }

        System.out.println(account);

        repository.save(account);
    }

    @SuppressWarnings("all")
    @Contract("_,_->sobaka")
    public void update(Long id, RawLoginData loginData) throws UsernameNotFoundException {
        if (!repository.existsById(id)) {
            throw new UsernameNotFoundException(id + " not found");
        }

        delete(id);

        Account newAccount = Account.fromRawLoginData(loginData);
        newAccount.setId(id);

        add(newAccount);
    }

    public Account get(Long id) throws UsernameNotFoundException {

        if (!repository.existsById(id)) {
            throw new UsernameNotFoundException(id + " not found");
        }

        return repository.getReferenceById(id);
    }

    public List<Account> getAll() {
        return repository.findAll();
    }

    public void delete(Long id) throws UsernameNotFoundException {

        if (!repository.existsById(id)) {
            throw new UsernameNotFoundException(id + " not found");
        }

        repository.deleteById(id);
    }


    public String getLoginById(Long id) {
        return repository.getReferenceById(id).getLogin();
    }

    public Long getIdByLogin(String login) throws NoSuchElementException {
        return repository.getByLogin(login).get().getId();
    }

}
