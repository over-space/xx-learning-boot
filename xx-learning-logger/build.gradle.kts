plugins {
    id("java")
}

dependencies {
    testImplementation(libs.bundles.junit.jupiter)
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}