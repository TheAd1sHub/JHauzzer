package com.skilkihodin.jhauzzer.models.accounts;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountsRepo extends JpaRepository<Account, String> {
}
