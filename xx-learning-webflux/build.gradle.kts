dependencies {
    testImplementation(project(mapOf("path" to ":xx-learning-logger")))
    implementation(project(mapOf("path" to ":xx-learning-common")))
    implementation(project(mapOf("path" to ":xx-learning-springboot")))

    developmentOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    // implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    // implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.alibaba.fastjson2:fastjson2:2.0.24")
    implementation(libs.bundles.alibaba.commons)
    implementation(libs.bundles.jdbc.database)
    implementation("org.springframework.boot:spring-boot-starter-webflux")
}