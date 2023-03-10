plugins {
    id("java")
}

group = "com.learning.boot"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")
    implementation("org.apache.zookeeper:zookeeper:3.8.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}