package com.skilkihodin.jhauzzer.model.accounts;

public class RawLoginData {

    private String login;
    private String password;
    private String accountType;


    public String getLogin() {
        return login;
    }
    public String getPassword() {
        return password;
    }
    public String getAccountType() {
        return accountType;
    }

    public void setLogin(String login) {
        this.login = login;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setAccountType(String account) {
        this.accountType = account;
    }

}
