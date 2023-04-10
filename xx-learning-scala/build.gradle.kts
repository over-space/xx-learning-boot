dependencies {
    api(libs.bundles.scala)
    api(libs.bundles.junit.jupiter)
    implementation(project(mapOf("path" to ":xx-learning-common")))
    implementation(project(mapOf("path" to ":xx-learning-logger")))
}