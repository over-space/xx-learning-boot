plugins{
    scala
}

dependencies {
    api(libs.bundles.logging.log4j)
    api(libs.bundles.junit.jupiter)
    api(libs.bundles.scala)
}

tasks.bootJar{
    enabled = false
}

tasks.jar{
    enabled = false
}