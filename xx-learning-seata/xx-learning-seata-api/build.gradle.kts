dependencies {
    implementation(project(mapOf("path" to ":xx-learning-springboot")))

    implementation("org.springframework.cloud:spring-cloud-starter-openfeign")
    implementation("com.alibaba.cloud:spring-cloud-starter-alibaba-nacos-discovery")
    implementation("com.alibaba.cloud:spring-cloud-starter-alibaba-nacos-config")
}

tasks.bootJar{
    enabled = false
}

tasks.jar{
    enabled = false
}