dependencies {
    implementation(project(mapOf("path" to ":xx-learning-seata:xx-learning-seata-api")))
    testImplementation(project(mapOf("path" to ":xx-learning-logger")))
    implementation(project(mapOf("path" to ":xx-learning-common")))
    implementation(project(mapOf("path" to ":xx-learning-springboot")))

    developmentOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    // implementation("org.springframework.cloud:spring-cloud-starter-openfeign")

    implementation("com.alibaba.fastjson2:fastjson2:2.0.24")
    implementation("io.seata:seata-all:1.6.1")
}
