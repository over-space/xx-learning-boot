plugins {
    id("java")
}

dependencies {
    testImplementation(project(mapOf("path" to ":xx-learning-logger")))
    testImplementation(libs.bundles.junit.jupiter)

    implementation(libs.bundles.google.zxing)
    implementation(libs.bundles.apache.commons)
    implementation("com.alibaba:fastjson:2.0.26")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}