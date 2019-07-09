import java.util.Date

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

val kluentVersion = "1.49"
val spekVersion =  "2.0.2"

plugins {
    kotlin("jvm") version "1.3.40"
    java
    `maven-publish`
    signing
}


allprojects {
    group = "no.nav.syfo.sm"
    version = "1.0.22"

    repositories {
        mavenCentral()
        jcenter()
    }

}

subprojects {
    apply(plugin = "kotlin")
    apply(plugin = "java")
    apply(plugin = "maven-publish")
    apply(plugin = "signing")

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

    signing {
        useInMemoryPgpKeys(System.getenv("GPG_KEY_BASE64"), System.getenv("GPG_PASSPHRASE"))
        sign(tasks["jar"])
    }

    publishing {
        repositories {
            maven {
                url = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2")
                this.credentials {
                    username = System.getenv("SONATYPE_USERNAME")
                    password = System.getenv("SONATYPE_PASSWORD")
                }
            }
        }
        publications {
            create<MavenPublication>("mavenJava") {
                from(components["java"])
                //artifact(tasks.getByName("sourcesJar"))
                pom {

                    name.set("SYFO mottak common")
                    description.set("Common functionality used between the syfo mottak apps")
                    url.set("https://github.com/navikt/syfosm-common")

                    licenses {
                        license {
                            name.set("MIT License")
                            url.set("https://opensource.org/licenses/MIT")
                        }
                    }

                    scm {
                        connection.set("scm:git:https://github.com/navikt/syfosm-common.git")
                        developerConnection.set("scm:git:https://github.com/navikt/syfosm-common.git")
                        url.set("https://github.com/navikt/syfosm-common")
                    }
                }
            }
        }
    }

    val generatePomPropertiesForJar by tasks.registering {
        val outputDir = file("$buildDir/pom-properties")
        outputDir.mkdirs()
        val outputFile = file("$outputDir/pom.properties")
        outputFile.writeText("""
#Generated by Gradle
#${Date()}
groupId=${project.group}
artifactId=${project.name}
version=${project.version}
""".trimIndent())
        outputs.file(outputFile)
    }

    tasks.withType<Jar> {
        val generatePomFileForMavenJavaPublication = tasks.getByName("generatePomFileForMavenJavaPublication")
        dependsOn(generatePomPropertiesForJar, generatePomFileForMavenJavaPublication)

        into("META-INF/maven/${project.group}/${project.name}") {
            from(generatePomPropertiesForJar)
            from(generatePomFileForMavenJavaPublication) {
                rename(".+", "pom.xml")
            }
        }
    }
}

tasks.create("printVersion") {
    doLast {
        println(project.version)
    }
}
