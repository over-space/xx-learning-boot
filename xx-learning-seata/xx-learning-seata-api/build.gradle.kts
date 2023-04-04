plugins {
    id("java")
}

dependencies {
    implementation(project(mapOf("path" to ":xx-learning-springboot")))

    implementation("org.springframework.cloud:spring-cloud-starter-netflix-ribbon:2.2.10.RELEASE")
    implementation("org.springframework.cloud:spring-cloud-starter-openfeign:3.1.6")

}

