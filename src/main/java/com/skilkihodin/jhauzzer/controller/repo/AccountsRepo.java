package com.skilkihodin.jhauzzer.controller.repo;

import com.skilkihodin.jhauzzer.exceptions.DataNotFoundException;
import com.skilkihodin.jhauzzer.model.accounts.Account;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountsRepo extends JpaRepository<Account, Long> {

    default Optional<Account> getByLogin(String login) throws DataNotFoundException {
        Account exampleAccount = new Account();
        exampleAccount.setLogin(login);

        return findOne(Example.of(exampleAccount));
    }

}
