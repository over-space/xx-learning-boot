plugins {
    id("java")
}

group = "com.learning-boot"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project(mapOf("path" to ":xx-learning-logger")))
    testImplementation(project(mapOf("path" to ":xx-learning-logger")))

//    implementation("org.apache.logging.log4j:log4j-core:2.20.0")
//    implementation("org.apache.logging.log4j:log4j-api:2.20.0")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}