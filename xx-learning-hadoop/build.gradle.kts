import org.springframework.boot.gradle.tasks.run.BootRun

dependencies {
    implementation(project(mapOf("path" to ":xx-learning-logger")))
    implementation(project(mapOf("path" to ":xx-learning-common")))
    implementation("org.apache.hadoop:hadoop-client:2.6.5")
}


tasks.withType<Jar> {
    enabled  = true
    manifest {
        attributes["Start-Class"] = "com.learning.hadoop.HadoopApplication"
    }
}

tasks.bootJar{
    enabled = false
}

tasks.jar{
    enabled = false
}