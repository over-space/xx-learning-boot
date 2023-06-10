plugins {
    java
    id("org.springframework.boot") version "2.7.10"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    kotlin("jvm") version "1.8.10"
    kotlin("plugin.spring") version "1.8.10"
    id("org.jetbrains.intellij") version "1.13.2" apply false
}

java.sourceCompatibility = JavaVersion.VERSION_11

extra["springCloudVersion"] = "2021.0.6"
extra["springCloudAlibabaVersion"] = "2021.0.5.0"

allprojects {

    group = "com.learning.boot"
    version = "1.0.0"

    repositories {
        gradlePluginPortal()
        google()
        mavenLocal()
        if (project.name.contains("plugin-client")) {
        } else {
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

    tasks.withType<Jar>() {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }
}

subprojects {

    apply {
        plugin("scala")
        plugin("org.springframework.boot")
        plugin("io.spring.dependency-management")
        plugin("org.jetbrains.kotlin.jvm")
        plugin("org.jetbrains.kotlin.plugin.spring")
    }

    dependencyManagement {
        imports {
            mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
            mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
            mavenBom("com.alibaba.cloud:spring-cloud-alibaba-dependencies:${property("springCloudAlibabaVersion")}")
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }

    tasks.bootJar{
        enabled = false
    }

    tasks.jar{
        enabled = false
    }
}

tasks.bootJar{
    enabled = false
}

tasks.jar{
    enabled = false
}