package com.skilkihodin.jhauzzer.dto;

import lombok.Data;

@Data
public final class RawLoginData {

    private String login;
    private String password;
    private String role;

}
