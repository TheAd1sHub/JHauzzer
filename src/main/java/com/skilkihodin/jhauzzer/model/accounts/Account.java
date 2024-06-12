package com.skilkihodin.jhauzzer.model.accounts;

import com.skilkihodin.jhauzzer.security.Encryptor;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

/*
{
    "login": "user",
    "password": "password",
    "accountType": "USER"
}
*/

@Entity
@Table(name = "accounts")
@Getter @Setter
public final class Account {
    public enum Type { USER, VIP_USER, WAREHOUSE_ADMIN }

    @Id
    @Column(name = "login")
    private String login;

    @Column(name = "password_hash")
    private String passwordHash;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private Type type;


    public static Account fromRawLoginData(@NotNull RawLoginData loginData) {

        Account result = new Account();

        result.login = loginData.getLogin();
        result.passwordHash = Encryptor.hashPassword(loginData.getPassword());

        Type accountType = null;
        for (Type type : Type.values()) {
            if (type.toString().equals(loginData.getAccountType())) {
                accountType = type;

                break;
            }
        }

        if (accountType == null) {
            throw new IllegalArgumentException("The given account type cannot be found: " + loginData.getAccountType());
        }

        result.type = accountType;

        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(login, account.login);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(login);
    }
}
