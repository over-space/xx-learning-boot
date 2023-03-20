plugins {
    id("java")
}

dependencies {
    testImplementation(project(mapOf("path" to ":xx-learning-logger")))
    implementation(project(mapOf("path" to ":xx-learning-common")))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.9.2")
    implementation("com.alibaba.fastjson2:fastjson2:2.0.24")
    implementation("com.google.guava:guava:31.1-jre")
    implementation("org.apache.commons:commons-lang3:3.12.0")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}