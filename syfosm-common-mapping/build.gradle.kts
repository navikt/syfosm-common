val fellesformatVersion = "1.0"
val sykmeldingVersion = "1.1-SNAPSHOT"

repositories {    
    maven(url = "https://repo.adeo.no/repository/maven-snapshots/")
    maven(url = "https://repo.adeo.no/repository/maven-releases/")
}

dependencies {
    implementation(project(":syfosm-common-models"))

    implementation("no.nav.syfo.tjenester:fellesformat:$fellesformatVersion")
    implementation("no.nav.helse.xml:sm2013:$sykmeldingVersion")
}

plugins {
    id("java")
    id("maven-publish")
}

publishing {
    repositories {
        maven {
            url = uri("https://maven.pkg.github.com/navikt")
            credentials {
                username = System.getenv("GITHUB_USERNAME")
                password = System.getenv("GITHUB_PASSWORD")
            }
        }
    }
    publications {
        create<MavenPublication>("mavenJava") {

            pom {
                name.set("github-package-registry-gradle")
                description.set("A test project for the maven-publish plugin")
                url.set("https://github.com/navikt/github-package-registry-gradle")
                licenses {
                    license {
                        name.set("MIT License")
                        url.set("https://opensource.org/licenses/MIT")
                    }
                }

                scm {
                    connection.set("scm:git:https://github.com/navikt/github-package-registry-gradle.git")
                    developerConnection.set("scm:git:https://github.com/navikt/github-package-registry-gradle.git")
                    url.set("https://github.com/navikt/github-package-registry-gradle")
                }
            }
            from(components["java"])
        }
    }
}