package com.skilkihodin.jhauzzer.controller.repo;

import com.skilkihodin.jhauzzer.model.accounts.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountsRepo extends JpaRepository<Account, Long> {
}
