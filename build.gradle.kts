import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

//import org.gradle.internal.impldep.org.joda.time.format.DateTimeFormatter

plugins {
	java
	id("org.springframework.boot") version "3.3.0"
	id("io.spring.dependency-management") version "1.1.5"
	//id("org.ajoberstar.grgit") version "4.1.1"
	kotlin("jvm")
}

group = "com.skilkihodin"
version = "1.33.7-LUNA"


java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}


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

}

tasks.withType<Test> {
	useJUnitPlatform()
}


// BUILD NAME GENERATION

val hashCharsAmount = 4
fun generateBuildName() : String {
	val dateTimeFormatter = DateTimeFormatter.ofPattern("ddMMyyHHmmss")
	val buildDate: String = LocalDateTime.now().format(dateTimeFormatter)

	val gitHashPart: String = Runtime.getRuntime()
		.exec("git log -1 --format=\"%<(${hashCharsAmount + 2},trunc)%H\"".split(' ').toTypedArray())
		.inputReader()
		.readLine()
		.substring(0, hashCharsAmount)

	val buildData: String = "$version-$buildDate$gitHashPart"

	return buildData
}


val buildName: String = generateBuildName()
val dtosBuildName: String = "$buildName-DTO"
