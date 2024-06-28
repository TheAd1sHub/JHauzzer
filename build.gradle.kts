import org.gradle.api.internal.file.copy.CopySpecInternal
import java.nio.file.Files
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

plugins {
	java
	id("org.springframework.boot") version "3.3.0"
	id("io.spring.dependency-management") version "1.1.5"
	kotlin("jvm")
	// distribution
	`java-library-distribution`
}


group = "com.skilkihodin"
version = "1.98.4-LUNA"


java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}


//sourceSets {
//	create("dto") {
//		java {
//			srcDir("src/main/java/")
//			include("com/skilkihodin/jhauzzer/dto/**/*.java")
//		}
//
//		tasks.jar {
//			archiveFileName.set("$buildName-dto.jar")
//		}
//	}
//}

// val dtoSourceSet: SourceSet = sourceSets["dto"]


dependencies {
	// External libs
	implementation(fileTree(mapOf("dir" to "lib", "include" to listOf("*.jar"))))

	// Databases
	implementation("com.mysql:mysql-connector-j:8.3.0")
	implementation("org.liquibase:liquibase-core")

	// Spring boot starter
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-jdbc")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-thymeleaf:3.3.0")

	// implementation("org.springframework.boot:spring-boot-starter-security") // THE ONES BELOW ARE USED INSTEAD
	implementation("org.springframework.security:spring-security-core:6.3.0")
	implementation("org.springframework.security:spring-security-config:6.3.0")
	implementation("org.springframework.security:spring-security-web:6.3.0")

	testImplementation("org.springframework.boot:spring-boot-starter-test")

	// Testing
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
	implementation(kotlin("stdlib-jdk8"))

	implementation("io.projectreactor.ipc:reactor-netty:0.7.15.RELEASE")

	implementation("org.springframework.boot:spring-boot-starter-webflux:3.3.0")
	implementation("org.modelmapper:modelmapper:3.2.0")

	// Lombok
	compileOnly("org.projectlombok:lombok:1.18.30")
	annotationProcessor("org.projectlombok:lombok:1.18.30")
	implementation("org.projectlombok:lombok:1.18.30")

	// При сборке отмечать дату Мажорная, минорная версия и доп. инфа, связанная с датой сборки (ДД-ММ-ГГ, ЧЧ-ММ, первые четыре символа из хэша коммита
	// Внешняя и на сам проект. Несколько сборок при билде: само приложение + либа для API-юзеров

	// JGit - DO NOT USE! Crashes Gradle
	// implementation("org.eclipse.jgit:org.eclipse.jgit:6.8.0.202311291450-r")



	// Lombok
	//add("dtoCompileOnly", "org.projectlombok:lombok:1.18.30")
	//add("dtoAnnotationProcessor", "org.projectlombok:lombok:1.18.30")
	//add("dtoImplementation", "org.projectlombok:lombok:1.18.30")

	//"dtoCompile("org.projectlombok:lombok:1.18.30")
	//"dtoAnnotationProcessor"("org.projectlombok:lombok:1.18.30")
	//"dtoImplementation"("org.projectlombok:lombok:1.18.30")


	//implementation(dtoSourceSet.output)
}



tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<Jar>() {

	duplicatesStrategy = DuplicatesStrategy.EXCLUDE

	manifest {
		attributes["Main-Class"] = "JHauzzerApplication"
	}

	configurations["compileClasspath"].forEach { file: File ->
		from(zipTree(file.absoluteFile))
	}
}

//#region BUILD FILE NAME GENERATION & ASSIGNMENT

val hashCharsAmount: UInt = 4U

fun generateBuildName() : String {
	val dateTimeFormatter = DateTimeFormatter.ofPattern("ddMMyyHHmmss")
	val buildDate: String = LocalDateTime.now().format(dateTimeFormatter)

	val gitHashPart: String = Runtime.getRuntime()
		.exec("git log -1 --format=\"%<(${hashCharsAmount + 2U},trunc)%H\"".split(' ').toTypedArray())
		.inputReader()
		.readLine()
		.substring(0, hashCharsAmount.toInt())

	val buildData: String = "$version-$buildDate$gitHashPart"

	return buildData
}

val buildName: String = generateBuildName()
val dtosBuildName: String = "$buildName-dto"


//distributions {
//	main {
//		distributionBaseName = buildName
//		contents {
//			from("src/main/java")
//		}
//
//	}
//
//	create("dto") {
//		distributionBaseName = dtosBuildName
//
//		contents {
//			from("src/main/java/com/skilkihodin/jhauzzer/dto")
//		}
//	}
//}


val mainBuildJar: Jar by tasks.creating(Jar::class) {
	archiveBaseName.set(buildName)
	archiveFileName.set("$buildName.jar")
	from(sourceSets["main"].output)

	doNotTrackState("Cuz I said so")
}

val dtoBuildJar: Jar by tasks.creating(Jar::class) {
	archiveBaseName.set(dtosBuildName)
	archiveFileName.set("$dtosBuildName.jar")

	val dtoClasses: CopySpec = copySpec {
		from(sourceSets["main"].output) {
			include("com/skilkihodin/jhauzzer/dto/**/*.class")
		}
	}
	//from({
	//	sourceSets["main"].output.classesDirs.asFileTree.matching {
	//		include("com/skilkihodin/jhauzzer/dto/**/*.class")
	//	}
	//})

	for (f in Files.walk(fileTree(dtoClasses as CopySpecInternal))) {
		println(f.path)
	}

	//from(fileTree(dtoClasses as CopySpecInternal).matching {
	//	include("**/*.class")
	//})
	with(dtoClasses)



	doNotTrackState("Cuz I said so")
	dependsOn(tasks["compileJava"])
	dependsOn(tasks["compileKotlin"])
	dependsOn(tasks["processResources"])
}

tasks.assemble {
	dependsOn(mainBuildJar, dtoBuildJar)
}




val buildDirectory = "${layout.buildDirectory.get()}\\libs"
mainBuildJar.destinationDirectory.set(file(buildDirectory))
dtoBuildJar.destinationDirectory.set(file(buildDirectory))

//tasks.jar {
//
//	archiveFileName.set("$buildName.jar")
//}

//val makeDtoJar by tasks.creating(Jar::class) {
//	archiveClassifier.set("dto")
//	from(sourceSets["dto"].output)
//	dependsOn(tasks["classes"])  // Ensure main classes are compiled first
//}

//tasks["assemble"].dependsOn(makeDtoJar)
//#endregion