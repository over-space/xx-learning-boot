dependencies {
    implementation(libs.bundles.jdbc.database)
    implementation(project(mapOf("path" to ":xx-learning-common")))
    testImplementation(project(mapOf("path" to ":xx-learning-scala")))

    implementation("org.apache.spark:spark-core_2.12:2.4.8")
    implementation("org.apache.spark:spark-sql_2.12:2.4.8")
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

tasks.bootJar{
    enabled = true
}

tasks.jar{
    enabled = false
}
