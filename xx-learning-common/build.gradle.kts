plugins {
    id("java")
}

dependencies {
    testImplementation(project(mapOf("path" to ":xx-learning-logger")))
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}