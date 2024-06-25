plugins {
	java
	id("org.springframework.boot") version "3.3.0"
	id("io.spring.dependency-management") version "1.1.5"
	kotlin("jvm")
}

group = "com.skilkihodin"
version = "0.0.1-SNAPSHOT"

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

	compileOnly("org.projectlombok:lombok:1.18.30")

	implementation("org.springframework.boot:spring-boot-starter-webflux:3.3.0")
	implementation("org.modelmapper:modelmapper:3.2.0")

	// Lombok
	annotationProcessor("org.projectlombok:lombok:1.18.30")
	implementation("org.projectlombok:lombok:1.18.30")

}

tasks.withType<Test> {
	useJUnitPlatform()
}
