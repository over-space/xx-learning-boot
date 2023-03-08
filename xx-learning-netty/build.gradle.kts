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
    implementation("io.netty:netty-all:4.1.89.Final")
    implementation("org.eclipse.jetty:jetty-server:9.4.51.v20230217")
    implementation("org.eclipse.jetty:jetty-webapp:9.4.51.v20230217")
    implementation("com.esotericsoftware:reflectasm:1.11.9")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}