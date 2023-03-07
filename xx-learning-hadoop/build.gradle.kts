plugins {
    id("java")
}

group = "com.learning-boot"
version = "1.0-SNAPSHOT"

repositories {
    maven {
        setUrl("https://maven.aliyun.com/nexus/content/groups/public/")
    }
    mavenCentral()
}

dependencies {
    implementation("org.apache.hadoop:hadoop-client:2.6.5")
    implementation("org.apache.logging.log4j:log4j-core:2.20.0")
    implementation("org.apache.logging.log4j:log4j-api:2.20.0")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}