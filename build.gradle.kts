plugins {
    id("java")
    kotlin("jvm") version "1.3.50"
}

group = "com.learning-boot"
version = "1.0-SNAPSHOT"

allprojects {
    repositories {
        if (!project.name.contains("plugin-client")) {
            mavenLocal()
            maven { setUrl("https://maven.aliyun.com/repository/public") }
            maven { setUrl("https://maven.aliyun.com/repository/central") }
            maven { setUrl("https://maven.aliyun.com/repository/spring") }
            maven { setUrl("https://maven.aliyun.com/repository/jcenter") }
            maven { setUrl("https://maven.aliyun.com/repository/apache-snapshots") }
            maven { setUrl("https://maven.aliyun.com/repository/google") }
            maven { setUrl("https://maven.aliyun.com/repository/releases") }
            maven { setUrl("https://maven.aliyun.com/repository/snapshots") }
            maven { setUrl("https://maven.aliyun.com/repository/grails-core") }
            maven { setUrl("https://maven.aliyun.com/repository/mapr-public") }
            maven { setUrl("https://maven.aliyun.com/repository/gradle-plugin") }
            maven { setUrl("https://maven.aliyun.com/repository/spring-plugin") }
        }
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")

    dependencies {
        implementation("org.apache.logging.log4j:log4j-core:2.20.0")
        implementation("org.apache.logging.log4j:log4j-api:2.20.0")
    }
}

tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "11"
        targetCompatibility = "11"
    }
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

