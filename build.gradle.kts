import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kluentVersion = "1.49"
val spekVersion =  "2.0.2"

plugins {
    kotlin("jvm") version "1.3.50"
    java
}

repositories {
    mavenCentral()
}

subprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")

    dependencies {
        implementation(kotlin("stdlib"))
        implementation(kotlin("reflect"))

        testImplementation("org.spekframework.spek2:spek-dsl-jvm:$spekVersion")
        testImplementation("org.amshove.kluent:kluent:$kluentVersion")
        testRuntimeOnly("org.spekframework.spek2:spek-runner-junit5:$spekVersion")
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
    }

    tasks.withType<Test> {
        useJUnitPlatform {
            includeEngines("spek2")
        }
        testLogging {
            showStandardStreams = true
        }
    }
}

tasks.create("printVersion") {
    doLast {
        println(project.version)
    }
}
