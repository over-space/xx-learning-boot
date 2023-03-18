plugins {
    id("java")
    id("scala")
}

dependencies {
    implementation("org.apache.spark:spark-core_2.12:2.4.8")
    implementation("org.scala-lang:scala-library:2.12.0")
    testImplementation("org.scalatestplus:junit-4-13_2.12:3.1.4.0")
    testImplementation("org.scalatest:scalatest_2.12:3.2.14")
    implementation("com.typesafe.akka:akka-actor_2.12:2.4.20")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}