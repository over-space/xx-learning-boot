plugins {
    id("java")
}

dependencies {
    implementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    compileOnly("org.junit.jupiter:junit-jupiter-engine:5.9.2")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}