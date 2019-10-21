import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kluentVersion = "1.49"
val spekVersion =  "2.0.2"
val junitJupiterVersion = "5.4.0"
val jacksonVersion = "2.9.8"

repositories {
    mavenCentral()
}

plugins {
    kotlin("jvm") version "1.3.50"
}

allprojects {
    group = "no.nav.helse"
    version = properties["version"] ?: "local-build"

    repositories {
        mavenCentral()
        jcenter()
    }
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")

    repositories {
        mavenCentral()
        jcenter()
    }

    dependencies {
        implementation(kotlin("stdlib"))
        implementation(kotlin("reflect"))

        testImplementation("org.spekframework.spek2:spek-dsl-jvm:$spekVersion")
        testImplementation("org.amshove.kluent:kluent:$kluentVersion")
        testRuntimeOnly("org.spekframework.spek2:spek-runner-junit5:$spekVersion")
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "11"
    }

    tasks.withType<Test> {
        useJUnitPlatform()
        testLogging {
            events("passed", "skipped", "failed")
        }
    }
}
dependencies {
    implementation(kotlin("stdlib-jdk8"))
}
