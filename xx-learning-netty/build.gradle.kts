plugins {
    id("java")
}

group = "com.learning-boot"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(mapOf("path" to ":xx-learning-common")))
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}