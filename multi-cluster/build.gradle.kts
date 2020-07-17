plugins {
    application
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

application {
    mainClassName = "org.mongodb.spring.multi.Application"
}