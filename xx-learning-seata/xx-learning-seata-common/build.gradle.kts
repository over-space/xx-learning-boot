dependencies {
    api(project(":xx-learning-springboot"))

    api("org.springframework.cloud:spring-cloud-starter-openfeign")
    api("org.springframework.cloud:spring-cloud-starter-loadbalancer")
    api("com.alibaba.cloud:spring-cloud-starter-alibaba-nacos-discovery")
    api("com.alibaba.cloud:spring-cloud-starter-alibaba-nacos-config")
}

tasks.bootJar{
    enabled = false
}

tasks.jar{
    enabled = true
}