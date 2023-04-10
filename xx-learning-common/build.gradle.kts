dependencies {
    implementation(libs.bundles.google.zxing)
    api(libs.bundles.apache.commons)
    api(libs.bundles.alibaba.commons)
    implementation(project(mapOf("path" to ":xx-learning-logger")))
}

tasks.bootJar{
    enabled = false
}

tasks.jar{
    enabled = false
}