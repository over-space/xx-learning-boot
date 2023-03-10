plugins {
    id("java")
}

group = "com.learning-boot"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    compileOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")

    implementation("org.apache.logging.log4j:log4j-core:2.20.0")
    implementation("org.apache.logging.log4j:log4j-api:2.20.0")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}