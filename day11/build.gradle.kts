import org.gradle.api.tasks.testing.logging.TestLogEvent.*

plugins {
    kotlin("jvm")
    application
}

repositories {
    jcenter()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation(project(":utilities"))
    implementation(project(":intcode"))
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}

application {
    mainClassName = "MainKt"
}
