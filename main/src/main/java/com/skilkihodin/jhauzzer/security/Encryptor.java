package com.skilkihodin.jhauzzer.security;

public final class Encryptor {

    private static HashGenerator passwordHashGenerator;

    static {
        passwordHashGenerator = new Crc32HashGenerator();
    }

    public static String hashPassword(String password) {
        return passwordHashGenerator.getHash(password);
    }

    private Encryptor() {}
}
