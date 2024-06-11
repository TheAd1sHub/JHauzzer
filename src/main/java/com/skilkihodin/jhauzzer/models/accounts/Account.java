package com.skilkihodin.jhauzzer.models.accounts;

//import org.jetbrains.NotNull;
import com.skilkihodin.jhauzzer.security.Encryptor;
import jakarta.persistence.*;

import java.util.IllegalFormatConversionException;


@Entity
@Table(name = "accounts")
public final class Account {
    public enum Type { USER, VIP_USER, WAREHOUSE_ADMIN }

    @Id
    @Column(name = "login")
    private String login;

    @Column(name = "password_hash")
    private String password;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private Type accountType;


    public static Account fromRawLoginData(RawLoginData loginData) {

        Account result = new Account();

        result.setLogin(loginData.getLogin());
        result.setPasswordHash(Encryptor.hashPassword(loginData.getPassword()));

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

        result.setAccountType(accountType);

        return result;
    }

    public String getLogin() {
        return login;
    }
    public String getPasswordHash() {
        return password;
    }
    public Type getAccountType() {
        return accountType;
    }

    public void setLogin(String login) {
        this.login = login;
    }
    public void setPasswordHash(String passwordHash) {
        this.password = passwordHash;
    }
    public void setAccountType(Type accountType) {
        this.accountType = accountType;
    }

}
