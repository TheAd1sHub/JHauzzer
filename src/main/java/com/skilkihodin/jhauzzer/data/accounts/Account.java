package com.skilkihodin.jhauzzer.data.accounts;

//import org.jetbrains.NotNull;
import jakarta.persistence.*;
import org.springframework.stereotype.Component;


@Entity
@Table(name = "accounts")
public final class Account {
    public enum Type { USER, VIP_USER, WAREHOUSE_ADMIN }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String login;

    @Column(name = "password_hash")
    private int passwordHash;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private Type accountType;


    public String getLogin() {
        return login;
    }
    public int getPasswordHash() {
        return passwordHash;
    }
    public Type getAccountType() {
        return accountType;
    }

    public void setLogin(String login) {
        this.login = login;
    }
    public void setPasswordHash(int passwordHash) {
        this.passwordHash = passwordHash;
    }
    public void setAccountType(Type accountType) {
        this.accountType = accountType;
    }

}
