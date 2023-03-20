plugins {
    id("java")
}

dependencies {
    testImplementation(libs.bundles.junit.jupiter)
    testImplementation(project(mapOf("path" to ":xx-learning-logger")))
    implementation(project(mapOf("path" to ":xx-learning-common")))
    implementation("com.alibaba.fastjson2:fastjson2:2.0.24")
    implementation("com.google.guava:guava:31.1-jre")
    implementation("org.apache.commons:commons-lang3:3.12.0")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}