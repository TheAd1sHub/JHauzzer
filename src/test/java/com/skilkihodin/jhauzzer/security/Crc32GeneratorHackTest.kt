package com.skilkihodin.jhauzzer.security

fun main() {
    val hashGenerator = Crc32HashGenerator()
    val passwordGenerator = PasswordGenerator(8)

    val targetHash: String = hashGenerator.getHash("password") // "fffea000"
    println("Target: $targetHash")

    var generatedPassword: String
    var iterationsCount: ULong = 0u
    do  {
        generatedPassword = passwordGenerator.generate()

        val matchesTargetHash: Boolean = hashGenerator.getHash(generatedPassword) == targetHash

        if ((++iterationsCount % 1_000_000u).toInt() == 0) {
            println("$iterationsCount iterations processed")
        }
    //println("Password: " + generatedPassword + "\tMatches hash: " + (if (matchesTargetHash) "yes" else "no"))
    } while (!matchesTargetHash);

    println(generatedPassword)
}

class PasswordGenerator(charactersCount: Int) {
    private val characters: String = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"

    private val charactersRange: IntRange = (0..charactersCount);

    public fun generate() : String {
        var password: String  = ""

        for (i in charactersRange) {
            password += characters[characters.indices.random()]
        }

        return password
    }
}