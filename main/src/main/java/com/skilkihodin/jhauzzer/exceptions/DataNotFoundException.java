package com.skilkihodin.jhauzzer.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class DataNotFoundException extends RuntimeException {
    private String message;

}
