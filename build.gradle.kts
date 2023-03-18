plugins {
    id("java")
    id("scala")
    id("application")
    kotlin("jvm") version "1.8.10"
    id("org.springframework.boot") version "2.7.9" apply false
    id("io.spring.dependency-management") version "1.1.0" apply false
}

allprojects {

    group = "com.learning-boot"
    version = "1.0.0"

    repositories {
        gradlePluginPortal()
        mavenLocal()
        if (project.name.contains("plugin-client")) {
        }else {
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

    tasks {
        withType<JavaCompile> {
            sourceCompatibility = "11"
            targetCompatibility = "11"
        }
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")

    dependencies {
        implementation("org.apache.logging.log4j:log4j-core:2.20.0")
        implementation("org.apache.logging.log4j:log4j-api:2.20.0")
    }
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

tasks.withType<Jar>() {
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
}