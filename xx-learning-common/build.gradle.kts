dependencies {
    implementation(libs.bundles.google.zxing)
    api(libs.bundles.apache.commons)
    api(libs.bundles.alibaba.commons)
    implementation(project(mapOf("path" to ":xx-learning-logger")))
    implementation("cn.hutool:hutool-all:5.8.19")
}

tasks.bootJar{
    enabled = false
}

tasks.jar{
    enabled = true
}