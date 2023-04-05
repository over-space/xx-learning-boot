plugins {
    id("java")
    id("scala")
    id("application")
}

dependencies {
    implementation(libs.bundles.jdbc.database)
    implementation(libs.bundles.logging.log4j)
    testImplementation(libs.bundles.junit.jupiter)

    implementation(project(mapOf("path" to ":xx-learning-common")))
    testImplementation(project(mapOf("path" to ":xx-learning-scala")))
    testImplementation(project(mapOf("path" to ":xx-learning-logger")))

    implementation("org.apache.spark:spark-core_2.12:2.4.8")
    implementation("org.apache.spark:spark-sql_2.12:2.4.8")
}


tasks.getByName<Test>("test") {
    useJUnitPlatform()
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

