plugins {
    id("java")
    id("scala")
}

dependencies {
    implementation("org.apache.spark:spark-core_2.12:2.4.8")

    implementation("org.scala-lang:scala-library:2.12.0")
    implementation("com.typesafe.akka:akka-actor_2.12:2.4.20")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.2")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.9.2")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}