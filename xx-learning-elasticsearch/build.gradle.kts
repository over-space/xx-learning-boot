plugins {
    id("java")
}


dependencies {
    implementation(libs.bundles.logging.log4j)
    testImplementation(libs.bundles.junit.jupiter)
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}