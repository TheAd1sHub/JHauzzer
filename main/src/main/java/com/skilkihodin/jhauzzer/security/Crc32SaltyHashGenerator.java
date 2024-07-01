package com.skilkihodin.jhauzzer.security;

public final class Crc32SaltyHashGenerator extends SaltyHashGenerator {

    private final long UINT_THRESHOLD = 2147483648L;
    private final int POLYNOMINAL = 0x04C11DB7;
    private final Crc32HashGenerator generator;


    public Crc32SaltyHashGenerator(String salt) {
        super(salt);
        generator = new Crc32HashGenerator();
    }


    @Override
    public String getHash(String original) {
        //long crc = generator.getHash(original + salt);
        long crc = crc32(original + salt);

        return Long.toHexString(crc);
    }

    private long crc32(String original) {
        long crc = Integer.MAX_VALUE;

        for (int i = 0; i < original.length(); i++) {
            crc ^= original.charAt(i);
            for (byte bit = 0; bit < 8; bit++) {
                if ((crc & 1) != 0) {
                    crc >>= 1;
                    crc = crc ^ POLYNOMINAL;
                }
                else {
                    crc >>= 1;
                }
            }
        }

        return crc ^ Integer.MAX_VALUE;
    }
}
