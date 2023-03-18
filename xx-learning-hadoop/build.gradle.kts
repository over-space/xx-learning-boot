plugins {
    id("java")
}

dependencies {
    implementation("org.apache.hadoop:hadoop-client:2.6.5")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}