plugins {
    id("java")
}

dependencies {
    testImplementation(project(mapOf("path" to ":xx-learning-logger")))
    testImplementation(libs.bundles.junit.jupiter)

    implementation(libs.bundles.google.zxing)
    implementation(libs.bundles.apache.commons)
    implementation(libs.bundles.alibaba.commons)
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}