// plugins {
//     java
//     id("org.springframework.boot") version "2.7.10" apply false
//     id("io.spring.dependency-management") version "1.1.0" apply false
//     kotlin("jvm") version "1.8.10"
//     kotlin("plugin.spring") version "1.8.10" apply false
// }
//
// group = "com.learning-boot"
// version = "1.0.0"
// java.sourceCompatibility = JavaVersion.VERSION_11
//
// allprojects {
//
//     group = "com.learning-boot"
//     version = "1.0.0"
//
//     tasks {
//         withType<JavaCompile> {
//             sourceCompatibility = "11"
//             targetCompatibility = "11"
//         }
//     }
//
//     tasks.withType<Jar>() {
//         duplicatesStrategy = DuplicatesStrategy.EXCLUDE
//     }
// }
//
// extra["springCloudVersion"] = "2021.0.6"
//
// subprojects {
//     apply{
//         plugin("org.springframework.boot")
//         plugin("io.spring.dependency-management")
//         plugin("org.jetbrains.kotlin.jvm")
//         plugin("org.jetbrains.kotlin.plugin.spring")
//     }
//
//     repositories {
//         gradlePluginPortal()
//         mavenLocal()
//         if (project.name.contains("plugin-client")) {
//         }else {
//             maven { setUrl("https://maven.aliyun.com/repository/public") }
//             maven { setUrl("https://maven.aliyun.com/repository/central") }
//             maven { setUrl("https://maven.aliyun.com/repository/spring") }
//             maven { setUrl("https://maven.aliyun.com/repository/jcenter") }
//             maven { setUrl("https://maven.aliyun.com/repository/apache-snapshots") }
//             maven { setUrl("https://maven.aliyun.com/repository/google") }
//             maven { setUrl("https://maven.aliyun.com/repository/releases") }
//             maven { setUrl("https://maven.aliyun.com/repository/snapshots") }
//             maven { setUrl("https://maven.aliyun.com/repository/grails-core") }
//             maven { setUrl("https://maven.aliyun.com/repository/mapr-public") }
//             maven { setUrl("https://maven.aliyun.com/repository/gradle-plugin") }
//             maven { setUrl("https://maven.aliyun.com/repository/spring-plugin") }
//         }
//         mavenCentral()
//     }
//
//     dependencies {
//         implementation("org.apache.logging.log4j:log4j-core:2.20.0")
//         implementation("org.apache.logging.log4j:log4j-api:2.20.0")
//     }
//
//     dependencyManagement {
//         imports {
//             mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
//         }
//     }
//
//     tasks.withType<Test> {
//         useJUnitPlatform()
//     }
// }


// project(":xx-learning-springboot"){
//     springBootDependencies()
// }
//
// project(":xx-learning-seata:xx-learning-seata-goods"){
//     springBootDependencies()
//     springCloudDependencies()
// }
//
// project(":xx-learning-seata:xx-learning-seata-order"){
//     springBootDependencies()
//     springCloudDependencies()
// }
//
// project(":xx-learning-seata:xx-learning-seata-pay"){
//     springBootDependencies()
//     springCloudDependencies()
// }
//
// project(":xx-learning-webflux"){
//     springBootDependencies()
// }
//
// fun springCloudDependencies() {
//     val springCloudVersion = "2021.0.5"
//
//     dependencies {
//         implementation(platform("org.springframework.cloud:spring-cloud-dependencies:${springCloudVersion}"))
//     }
// }
//
// fun springBootDependencies(){
//     apply(plugin = "org.springframework.boot")
//     apply(plugin = "io.spring.dependency-management")
//     apply(plugin = "org.jetbrains.kotlin.jvm")
//     apply(plugin = "org.jetbrains.kotlin.plugin.spring")
//     apply(plugin = "java")
//
//     dependencies {
//         implementation(libs.bundles.alibaba.commons)
//         implementation(libs.bundles.jdbc.database)
//
//         // developmentOnly("org.springframework.boot:spring-boot-devtools")
//         testImplementation("org.springframework.boot:spring-boot-starter-test")
//         implementation("org.springframework.boot:spring-boot-starter-data-jpa")
//         implementation("org.springframework.boot:spring-boot-starter-web")
//
//         implementation("com.alibaba.fastjson2:fastjson2:2.0.24")
//
//         testImplementation(project(mapOf("path" to ":xx-learning-logger")))
//         implementation(project(mapOf("path" to ":xx-learning-common")))
//     }
// }
plugins {
    java
    id("org.springframework.boot") version "2.7.10"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    kotlin("jvm") version "1.8.10"
    kotlin("plugin.spring") version "1.8.10"
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

    dependencies {
        implementation("org.scala-lang:scala-library:2.12.0")
        implementation("com.typesafe.akka:akka-actor_2.12:2.4.20")
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
}

tasks.bootJar{
    enabled = false
}

tasks.jar{
    enabled = false
}