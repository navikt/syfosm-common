import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kluentVersion = "1.73"
val kotlinVersion = "1.9.20"
val kotestVersion = "5.7.2"


repositories {
    mavenCentral()
}

plugins {
    kotlin("jvm") version "1.9.20"
}

allprojects {
    group = "no.nav.helse"
    version = properties["version"] ?: "local-build"

    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")

    repositories {
        mavenCentral()
    }

    dependencies {
        implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")

        testImplementation("io.kotest:kotest-runner-junit5:$kotestVersion")
        testImplementation("org.amshove.kluent:kluent:$kluentVersion")
    }

    tasks {

        compileKotlin {
            kotlinOptions.jvmTarget = JavaVersion.VERSION_17.toString()
        }

        compileTestKotlin {
            kotlinOptions.jvmTarget = JavaVersion.VERSION_17.toString()
        }

        test {
            useJUnitPlatform {}
            testLogging {
                events("skipped", "failed")
                showStackTraces = true
                exceptionFormat = org.gradle.api.tasks.testing.logging.TestExceptionFormat.FULL
            }
        }
    }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
}
