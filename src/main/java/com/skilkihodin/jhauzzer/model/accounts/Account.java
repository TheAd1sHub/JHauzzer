package com.skilkihodin.jhauzzer.model.accounts;

import com.skilkihodin.jhauzzer.security.Encryptor;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/*
{
    "login": "user",
    "password": "password",
    "role": "USER"
}
*/

@Entity
@Table(name = "accounts")
@Getter @Setter
public final class Account implements UserDetails {
    public enum Type { ADMIN, CUSTOMER, VIP_CUSTOMER, WAREHOUSE_ADMIN }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String login;

    @Column(name = "password_hash")
    private String passwordHash;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Type role;


    public static Account fromRawLoginData(@NotNull RawLoginData loginData) {

        Account result = new Account();

        result.login = loginData.getLogin();
        result.passwordHash = Encryptor.hashPassword(loginData.getPassword());

        Type accountType = null;
        for (Type type : Type.values()) {
            if (type.toString().equals(loginData.getRole())) {
                accountType = type;

                break;
            }
        }

        if (accountType == null) {
            throw new IllegalArgumentException("The given account role cannot be found: " + loginData.getRole());
        }

        result.role = accountType;

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

    @Override
    public String toString() {
        return String.format(
                "{\nlogin: %s\npasswordHash: %s\nrole: %s\n}",
                login, passwordHash, role.name());
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return passwordHash;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
