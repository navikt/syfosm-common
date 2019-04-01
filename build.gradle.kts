import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.21"
    java
    `maven-publish`
}


allprojects {
    group = "no.nav.syfo.sm"
    version = "1.0.1"

    repositories {
        mavenCentral()
    }

}

subprojects {
    apply(plugin = "kotlin")
    apply(plugin = "java")
    apply(plugin = "maven-publish")

    dependencies {
        implementation(kotlin("stdlib-jdk8"))
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.8"
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
