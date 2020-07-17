plugins {
    id("io.freefair.lombok") version "5.1.0"
    id("org.springframework.boot") version "2.3.1.RELEASE"
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
}

val log4Version = "2.13.3"
val junitVersion = "5.6.2"
val assertjVersion = "3.16.1"

allprojects {

    apply(plugin = "java")
    apply(plugin = "io.freefair.lombok")
    apply(plugin = "org.springframework.boot")
    apply(plugin = "io.spring.dependency-management")

    repositories {
        mavenCentral()
        jcenter()
    }

//    dependencies {
////        "implementation"("org.springframework.retry:spring-retry")
////        "implementation"("org.springframework.boot:spring-boot-starter-data-mongodb")
//        "implementation"("org.springframework.boot:spring-boot-starter-log4j2")
////        "runtimeOnly"("org.springframework.boot:spring-boot-starter-aop")
//
//        "testImplementation"("org.junit.jupiter:junit-jupiter:$junitVersion")
//        "testImplementation"("org.assertj:assertj-core:$assertjVersion")
////        testImplementation("io.projectreactor:reactor-test")
//        "testImplementation"("org.springframework.boot:spring-boot-starter-test")
//    }
//
//    configurations {
//        "implementation" {
//            exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
//            exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
//        }
//    }
//
//    tasks.withType<Test> {
//        useJUnitPlatform()
//    }
}

subprojects {
    group = "org.mongodb.spring"
    version = "0.0.1-SNAPSHOT"

    dependencies {
//        "implementation"("org.springframework.retry:spring-retry")
//        "implementation"("org.springframework.boot:spring-boot-starter-data-mongodb")
        "implementation"("org.springframework.boot:spring-boot-starter-log4j2")
//        "runtimeOnly"("org.springframework.boot:spring-boot-starter-aop")

        "testImplementation"("org.junit.jupiter:junit-jupiter:$junitVersion")
        "testImplementation"("org.assertj:assertj-core:$assertjVersion")
//        testImplementation("io.projectreactor:reactor-test")
        "testImplementation"("org.springframework.boot:spring-boot-starter-test")
    }

    configurations {
        "implementation" {
            exclude(group = "org.springframework.boot", module = "spring-boot-starter-logging")
            exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

