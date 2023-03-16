plugins {
    java
    id("org.springframework.boot") version "2.7.9"
    id("io.spring.dependency-management") version "1.1.0"
}

group = "com.learning-boot"
version = "1.0.0"

dependencies {
    testImplementation(project(mapOf("path" to ":xx-learning-logger")))
    implementation(project(mapOf("path" to ":xx-learning-common")))
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation("com.alibaba.fastjson2:fastjson2:2.0.24")
    implementation("com.google.guava:guava:31.1-jre")

    implementation("org.springframework.kafka:spring-kafka:2.9.6")
    implementation("org.apache.rocketmq:rocketmq-client:4.9.4")
    implementation("com.lmax:disruptor:3.4.4")
}


tasks.withType<Test> {
    useJUnitPlatform()
}
