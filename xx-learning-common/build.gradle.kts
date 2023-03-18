plugins {
    id("java")
}

dependencies {
    implementation(project(mapOf("path" to ":xx-learning-logger")))
    testImplementation(project(mapOf("path" to ":xx-learning-logger")))
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}