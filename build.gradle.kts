import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.7.10"
}

group = "dev.hoony9x"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.javaoperatorsdk:operator-framework:3.1.0")
    implementation("io.javaoperatorsdk:micrometer-support:3.1.0")
    implementation("org.takes:takes:1.21.1")
    implementation("org.apache.logging.log4j:log4j-slf4j-impl:2.18.0")
    implementation("org.slf4j:slf4j-api:1.7.36")
    implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.13.3")
    implementation("io.fabric8:crd-generator-apt:6.0.0")

    annotationProcessor("io.javaoperatorsdk:operator-framework:3.1.0")
    annotationProcessor("io.fabric8:crd-generator-apt:6.0.0")

    testImplementation(kotlin("test"))
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("io.javaoperatorsdk:operator-framework-junit-5:3.1.0")
    testImplementation("org.awaitility:awaitility:4.1.0")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "17"
}