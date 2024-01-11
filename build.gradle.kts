val kluentVersion = "1.73"
val kotlinVersion = "1.9.22"
val junitJupiterVersion = "5.10.1"
val javaVersion = JavaVersion.VERSION_21

repositories {
    mavenCentral()
}

plugins {
    kotlin("jvm") version "1.9.22"
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

        testImplementation("org.junit.jupiter:junit-jupiter:$junitJupiterVersion")

        testImplementation("org.amshove.kluent:kluent:$kluentVersion")
        testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    }

    tasks {

        compileKotlin {
            kotlinOptions.jvmTarget = javaVersion.toString()
        }

        compileTestKotlin {
            kotlinOptions.jvmTarget = javaVersion.toString()
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
