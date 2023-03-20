plugins {
    id("java")
    id("scala")
}

dependencies {
    implementation(libs.bundles.scala)
    implementation(libs.bundles.logging.log4j)
    implementation(libs.bundles.junit.jupiter)
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}