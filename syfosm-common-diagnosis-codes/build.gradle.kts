val jacksonVersion = "2.13.2"
val jacksonPatchVersion = "2.13.2.2"
val jacksonBomVersion = "2.13.2.20220328"


plugins {
    id("java")
    id("maven-publish")
}

dependencies {
    api("com.fasterxml.jackson.module:jackson-module-kotlin:$jacksonVersion")
    api("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:$jacksonVersion")
    implementation("com.fasterxml.jackson:jackson-bom:$jacksonBomVersion")
    implementation("com.fasterxml.jackson.core:jackson-databind:$jacksonPatchVersion")
    implementation(project(":syfosm-common-models"))
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
                name.set("syfosm-common-diagnosis-codes")
                description.set("Bibliotek for diagnosekoder for sykmeldingdomenet")
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