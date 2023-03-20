plugins {
    id("java")
}

dependencies {
    testImplementation(libs.bundles.junit.jupiter)
    implementation("org.apache.zookeeper:zookeeper:3.8.1")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}