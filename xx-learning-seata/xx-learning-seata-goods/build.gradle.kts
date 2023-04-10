dependencies {
    implementation(project(mapOf("path" to ":xx-learning-seata:xx-learning-seata-common")))
    implementation(project(mapOf("path" to ":xx-learning-springboot")))
    implementation("io.seata:seata-all:1.6.1")
}
