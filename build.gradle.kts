import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kluentVersion = "1.49"
val spekVersion =  "2.0.2"

plugins {
    kotlin("jvm") version "1.3.30"
    java
    `maven-publish`
}


allprojects {
    group = "no.nav.syfo.sm"
    version = "1.0.18"

    repositories {
        mavenCentral()
        jcenter()
    }

}

subprojects {
    apply(plugin = "kotlin")
    apply(plugin = "java")
    apply(plugin = "maven-publish")

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

    publishing {
        publications {
            create<MavenPublication>("maven") {
                groupId = project.group.toString()
                artifactId = project.name
                version = project.version.toString()

                from(components["java"])
            }
        }

        repositories {
            maven {
                val releaseType = when (project.version.toString().endsWith("-SNAPSHOT", false)){
                    true -> "snapshots"
                    else -> "releases" }
                url = uri("https://repo.adeo.no/repository/maven-$releaseType/")
            }
        }
    }
}

tasks.create("printVersion") {
    doLast {
        println(project.version)
    }
}
