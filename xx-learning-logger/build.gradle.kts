plugins {
    id("java")
    id("scala")
}

dependencies {
    implementation(libs.bundles.logging.log4j)
    implementation(libs.bundles.junit.jupiter)
    implementation(libs.bundles.scala)
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}