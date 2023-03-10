plugins {
    id("java")
}

group = "com.learning-boot"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(project(mapOf("path" to ":xx-learning-logger")))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.9.2")

    implementation(project(mapOf("path" to ":xx-learning-common")))
    implementation("org.apache.logging.log4j:log4j-core:2.20.0")
    implementation("org.apache.logging.log4j:log4j-api:2.20.0")

    implementation("org.apache.spark:spark-core_2.12:2.4.5")
}


tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

