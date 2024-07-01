package com.skilkihodin.jhauzzer.security

fun main() {
    val hashGenerator = Crc32HashGenerator()
    val passwordGenerator = PasswordGenerator(8)

    val targetHash: String = hashGenerator.getHash("password")
    println("Target: $targetHash")

    var generatedPassword: String
    var iterationsCount: ULong = 0u
    do  {
        generatedPassword = passwordGenerator.generate()

        val matchesTargetHash: Boolean = hashGenerator.getHash(generatedPassword) == targetHash

        if ((++iterationsCount % 1_000_000u).toInt() == 0) {
            println("$iterationsCount iterations processed")
        }
    } while (!matchesTargetHash);

    println("Found: $generatedPassword")
}

class PasswordGenerator(charactersCount: Int) {
    public companion object {
        private const val CHARACTERS: String = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"
    }

    private val charactersRange: IntRange = (0..charactersCount)

    public fun generate() : String {
        var password: String  = ""

        for (i in charactersRange) {
            password += CHARACTERS.random()//[CHARACTERS.indices.random()]
        }

        return password
    }
}