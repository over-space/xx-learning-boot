plugins {
    id("java")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

dependencies {
    implementation(project(mapOf("path" to ":xx-learning-springboot")))

    implementation("org.springframework.cloud:spring-cloud-starter-netflix-ribbon:2.2.10.RELEASE")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign:3.1.6")
    implementation("org.springframework.cloud:spring-cloud-netflix-core:2.2.10.RELEASE")
    implementation("org.springframework.cloud:spring-cloud-starter-feign:1.4.7.RELEASE")

    implementation("com.alibaba.cloud:spring-cloud-starter-alibaba-nacos-discovery:2021.1")
    implementation("com.alibaba.cloud:spring-cloud-starter-alibaba-nacos-config:2021.1")
}

