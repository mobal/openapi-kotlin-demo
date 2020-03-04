import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("idea")
    id("jacoco")
    id("org.jlleitschuh.gradle.ktlint") version "9.2.1"
    id("org.sonarqube") version "2.7.1"
    id("org.springframework.boot") version "2.2.5.RELEASE"
    id("io.spring.dependency-management") version "1.0.9.RELEASE"
    id("org.jetbrains.kotlin.plugin.jpa") version "1.3.61"
    kotlin("jvm") version "1.3.61"
    kotlin("plugin.spring") version "1.3.61"
}

group = "hu.netcode"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

val developmentOnly by configurations.creating
configurations {
    all {
        exclude(group = "org.mockito")
    }
    runtimeClasspath {
        extendsFrom(developmentOnly)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.flywaydb:flyway-core")
    implementation("org.hibernate.validator:hibernate-validator:6.1.2.Final")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.springdoc:springdoc-openapi-ui:1.2.33")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    runtimeOnly("com.h2database:h2")
    testImplementation("com.ninja-squad:springmockk:2.0.0")
    testImplementation("io.mockk:mockk:1.9.3")
    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

tasks.withType<JacocoReport> {
    reports {
        xml.isEnabled = true
    }
}

jacoco {
    toolVersion = "0.8.5"
}

sonarqube {
    properties {
        property("sonar.coverage.jacoco.xmlReportPaths", "build/reports/jacoco/test/jacocoTestReport.xml")
        property("sonar.java.binaries", "build/classes")
        property("sonar.java.coveragePlugin", "jacoco")
        property("sonar.jacoco.reportPath", "build/jacoco/test.exec")
        property("sonar.junit.reportPath", "build/test-results/test")
        property("sonar.language", "kotlin")
        property("sonar.sources", "src/main/kotlin")
    }
}
