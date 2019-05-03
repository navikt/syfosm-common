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
