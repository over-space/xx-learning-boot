dependencies {
    testImplementation(libs.bundles.junit.jupiter)
    implementation(libs.bundles.google.zxing)
    implementation(libs.bundles.apache.commons)
    implementation(libs.bundles.alibaba.commons)
    implementation(libs.bundles.logging.log4j)
    testImplementation(project(mapOf("path" to ":xx-learning-logger")))
}

tasks.bootJar{
    enabled = false
}

tasks.jar{
    enabled = false
}