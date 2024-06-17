package com.skilkihodin.jhauzzer.model.accounts;

import lombok.Data;

@Data
public class RawLoginData {

    private String login;
    private String password;
    private String role;

}
