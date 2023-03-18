plugins {
    id("java")
    id("scala")
    id("application")
}

dependencies {
    implementation(libs.bundles.jdbcDatabase)
    testImplementation(project(mapOf("path" to ":xx-learning-logger")))
    implementation(project(mapOf("path" to ":xx-learning-common")))
    implementation(project(mapOf("path" to ":xx-learning-scala")))

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.9.2")

    implementation("org.apache.logging.log4j:log4j-core:2.20.0")
    implementation("org.apache.logging.log4j:log4j-api:2.20.0")

    implementation("org.apache.spark:spark-core_2.12:2.4.8")
    implementation("org.scala-lang:scala-library:2.12.0")
    testImplementation("org.scalatestplus:junit-4-13_2.12:3.1.4.0")
    testImplementation("org.scalatest:scalatest_2.12:3.2.14")
    implementation("com.typesafe.akka:akka-actor_2.12:2.4.20")
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

