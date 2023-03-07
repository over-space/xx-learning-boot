plugins {
    java
    id("org.springframework.boot") version "2.7.9"
    id("io.spring.dependency-management") version "1.1.0"
}

group = "com.learning-boot"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
//    runtimeOnly("com.mysql:mysql-connector-j")
    implementation("com.h2database:h2:2.1.214")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}


tasks.withType<Test> {
    useJUnitPlatform()
}
