val coroutinesVersion = "1.0.1"
val ktorVersion = "1.2.0"
val logbackVersion = "1.2.3"
val logstashEncoderVersion = "5.1"


plugins {
    id("java")
    id("maven-publish")
    id("org.sonarqube") version "2.7"
}

dependencies {
    implementation(project(":syfosm-common-metrics"))

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

    implementation("io.ktor:ktor-client-cio:$ktorVersion")

    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("net.logstash.logback:logstash-logback-encoder:$logstashEncoderVersion")
}

subprojects {
    properties["sonarHost"]?.let { host ->
        sonarqube {
            properties {
                property("sonar.sourceEncoding", "UTF-8")
                property("sonar.host.url", host)
            }
        }
    }
}

publishing {
    repositories {
        maven {
            url = uri("https://maven.pkg.github.com/navikt/helse-sykdomstidslinje")
            credentials {
                username = System.getenv("GITHUB_USERNAME")
                password = System.getenv("GITHUB_PASSWORD")
            }
        }
    }
    publications {
        create<MavenPublication>("mavenJava") {

            pom {
                name.set("helse-sykdomstidslinje")
                description.set("Bibliotek for tidslinjer av intervaller relatert til sykefravær")
                url.set("https://github.com/navikt/helse-sykdomstidslinje")
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }

                scm {
                    connection.set("scm:git:https://github.com/navikt/helse-sykdomstidslinje.git")
                    developerConnection.set("scm:git:https://github.com/navikt/helse-sykdomstidslinje.git")
                    url.set("https://github.com/navikt/helse-sykdomstidslinje")
                }
            }
            from(components["java"])
        }
    }
}