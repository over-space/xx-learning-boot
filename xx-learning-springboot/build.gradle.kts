dependencies {
    testImplementation(project(mapOf("path" to ":xx-learning-logger")))

    api(project(":xx-learning-common"))
    api("org.springframework.boot:spring-boot-starter-data-jpa")
    api("org.springframework.boot:spring-boot-starter-webflux")
    api("org.springframework.cloud:spring-cloud-starter-gateway")
    api("org.springframework.cloud:spring-cloud-starter-openfeign")
    api("org.springframework.cloud:spring-cloud-starter-bootstrap")
    runtimeOnly(libs.bundles.jdbc.database)
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
}

tasks.bootJar{
    enabled = false
}

tasks.jar{
    enabled = true
}