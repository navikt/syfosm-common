val cxfVersion = "3.3.1"

plugins {
    id("java")
    id("maven-publish")
    id("org.sonarqube") version "2.7"
}

dependencies {
    api("org.apache.cxf:cxf-rt-frontend-jaxws:$cxfVersion")
    api("org.apache.cxf:cxf-rt-features-logging:$cxfVersion")
    api("org.apache.cxf:cxf-rt-transports-http:$cxfVersion")
    api("org.apache.cxf:cxf-rt-ws-security:$cxfVersion")
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
                name.set("syfosm-common-ws")
                description.set("Bibliotek for web-services implemenering i sykmeldings domenet")
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