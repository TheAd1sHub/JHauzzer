package com.skilkihodin.jhauzzer.security;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public final class Crc32SaltyHashGeneratorTest {

    private static Crc32SaltyHashGenerator hashGenerator;


    private String salt1 = "Salt",
                   salt2 = "Ultraflex";

    @Test
    public void testHashingSalt1Number1() {
        hashGenerator = new Crc32SaltyHashGenerator(salt1);

        String result = hashGenerator.getHash("Hello");

        assertEquals("3d878297", result);
    }
}
