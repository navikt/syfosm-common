val ktorVersion = "1.2.3"

repositories {
    maven(url = "https://kotlin.bintray.com/kotlinx")
}

plugins {
    id("java")
    id("maven-publish")
    id("org.sonarqube") version "2.7"
}

dependencies {
    implementation("io.ktor:ktor-client-cio:$ktorVersion")
    implementation("io.ktor:ktor-client-jackson:$ktorVersion")
    implementation("io.ktor:ktor-client-auth-jvm:$ktorVersion")
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
            url = uri("https://maven.pkg.github.com/navikt/syfosm-common")
            credentials {
                username = System.getenv("GITHUB_USERNAME")
                password = System.getenv("GITHUB_PASSWORD")
            }
        }
    }
    publications {
        create<MavenPublication>("mavenJava") {

            pom {
                name.set("syfoam-common-networking")
                description.set("Bibliotek for standar nettverksoppsett for sykmeldings domentet")
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
            from(components["java"])
        }
    }
}