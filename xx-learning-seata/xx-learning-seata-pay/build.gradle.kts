plugins {
    java
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

dependencies {
    testImplementation(project(mapOf("path" to ":xx-learning-logger")))
    implementation(project(mapOf("path" to ":xx-learning-common")))
    implementation(project(mapOf("path" to ":xx-learning-springboot")))
    implementation(project(mapOf("path" to ":xx-learning-seata:xx-learning-seata-api")))

    developmentOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.alibaba.fastjson2:fastjson2:2.0.24")
    implementation(libs.bundles.alibaba.commons)
    implementation(libs.bundles.jdbc.database)

    implementation("io.seata:seata-all:1.6.1")
}


tasks.withType<Test> {
    useJUnitPlatform()
}
