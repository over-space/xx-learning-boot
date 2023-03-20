plugins {
    id("java")
}

dependencies {
    implementation(libs.bundles.logging.log4j)
    implementation(libs.bundles.junit.jupiter)
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}