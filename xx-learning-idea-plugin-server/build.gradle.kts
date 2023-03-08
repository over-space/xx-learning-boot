plugins {
    java
    id("org.springframework.boot") version "2.7.9"
    id("io.spring.dependency-management") version "1.1.0"
}

group = "com.learning-boot"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.alibaba.fastjson2:fastjson2:2.0.24")
    implementation(libs.bundles.jdbcDatabase)
}


tasks.withType<Test> {
    useJUnitPlatform()
}
