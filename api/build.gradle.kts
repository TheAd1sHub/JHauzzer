plugins {
    id("java")
}

group = "com.skilkihodin"
version = "1.98.4-LUNA"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    compileOnly("org.projectlombok:lombok:1.18.30")
    annotationProcessor("org.projectlombok:lombok:1.18.30")
    implementation("org.projectlombok:lombok:1.18.30")
}

tasks.test {
    useJUnitPlatform()
}