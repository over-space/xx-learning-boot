dependencies {
    implementation(project(mapOf("path" to ":xx-learning-seata:xx-learning-seata-common")))
    testImplementation(project(mapOf("path" to ":xx-learning-logger")))
    implementation(project(mapOf("path" to ":xx-learning-common")))

    developmentOnly("org.springframework.boot:spring-boot-devtools")
    implementation("io.seata:seata-all:1.6.1")
}
