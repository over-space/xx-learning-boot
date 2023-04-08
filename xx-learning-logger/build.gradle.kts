dependencies {
    implementation(libs.bundles.logging.log4j)
    implementation(libs.bundles.junit.jupiter)
    implementation(libs.bundles.scala)
}

tasks.bootJar{
    enabled = false
}

tasks.jar{
    enabled = false
}