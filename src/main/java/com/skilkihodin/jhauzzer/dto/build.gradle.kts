import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

group = "com.skilkihodin.jhauzzer.dto"
version = "3.0.0"

//region BUILD FILE NAME GENERATION & ASSIGNMENT

val HASH_CHARS_AMOUNT: UInt = 4U
fun generateBuildName() : String {
    val dateTimeFormatter = DateTimeFormatter.ofPattern("ddMMyyHHmmss")
    val buildDate: String = LocalDateTime.now().format(dateTimeFormatter)

    val gitHashPart: String = Runtime.getRuntime()
        .exec("git log -1 --format=\"%<(${HASH_CHARS_AMOUNT + 2U},trunc)%H\"".split(' ').toTypedArray())
        .inputReader()
        .readLine()
        .substring(0, HASH_CHARS_AMOUNT.toInt())

    val buildData: String = "$version-$buildDate$gitHashPart"

    return buildData
}

val buildName: String = generateBuildName()
val dtosBuildName: String = "$buildName-DTO"

tasks.jar {
    manifest {

    }

    archiveFileName.set("$dtosBuildName.jar")
}
//endregion