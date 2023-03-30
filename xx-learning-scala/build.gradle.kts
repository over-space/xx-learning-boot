plugins {
    id("java")
    id("scala")
}

dependencies {
    implementation(libs.bundles.scala)
    implementation(libs.bundles.logging.log4j)
    implementation(libs.bundles.junit.jupiter)
    testImplementation(project(mapOf("path" to ":xx-learning-logger")))
    implementation(project(mapOf("path" to ":xx-learning-common")))
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}