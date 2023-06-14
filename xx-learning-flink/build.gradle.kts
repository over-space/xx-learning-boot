dependencies {
    testImplementation(libs.bundles.junit.jupiter)
    implementation(project(mapOf("path" to ":xx-learning-logger")))
    implementation(project(mapOf("path" to ":xx-learning-common")))
    implementation(project(mapOf("path" to ":xx-learning-scala")))

    implementation("org.apache.flink:flink-scala_2.12:1.15.4")
    implementation("org.apache.flink:flink-streaming-scala_2.12:1.15.4")
    implementation("org.apache.flink:flink-clients:1.15.4")
    implementation("org.apache.kafka:kafka_2.12:3.4.1")
//    implementation("org.apache.flink:flink-connector-kafka_2.12:1.15.4")
    testImplementation("org.apache.flink:flink-runtime-web:1.15.4")
}
