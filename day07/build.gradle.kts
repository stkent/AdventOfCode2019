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
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.2")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.5.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.5.2")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }

    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }

    test {
        useJUnitPlatform()
        testLogging.events = setOf(
            PASSED,
            FAILED,
            SKIPPED,
            STANDARD_OUT,
            STANDARD_ERROR
        )
    }
}

application {
    mainClassName = "MainKt"
}