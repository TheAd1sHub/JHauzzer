package com.skilkihodin.jhauzzer.security


class Crc32HashGenerator : HashGenerator() {

    private val POLYNOMINAL: UInt = 0x04C11DB7u

    override fun getHash(source: String) : String {
        var crc: UInt = UInt.MAX_VALUE

        for (char in source.chars()) {
            crc = crc xor char.toUInt();
            if (char.toInt().takeHighestOneBit() == 1) {
                crc = (crc shl 1) xor POLYNOMINAL
            } else {
                crc = crc shl 1;
            }
        }

        return crc.toString(16)
    }
}