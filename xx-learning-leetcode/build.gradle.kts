dependencies {
    testImplementation(libs.bundles.junit.jupiter)
    testImplementation(libs.bundles.apache.commons)
    implementation(libs.bundles.scala)
    implementation(libs.bundles.alibaba.commons)
    testImplementation(project(mapOf("path" to ":xx-learning-logger")))
    implementation(project(mapOf("path" to ":xx-learning-common")))
    implementation(project(mapOf("path" to ":xx-learning-scala")))
    implementation("com.google.guava:guava:31.1-jre")
}

tasks.withType<Jar>() {

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE

    manifest {
        attributes["Main-Class"] = "MainKt"
    }

    configurations["compileClasspath"].forEach { file: File ->
        from(zipTree(file.absoluteFile))
    }
}