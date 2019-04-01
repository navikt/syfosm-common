val coroutinesVersion = "1.0.1"
val ktorVersion = "1.1.3"
val logbackVersion = "1.2.3"
val logstashEncoderVersion = "5.1"

dependencies {
    implementation(project(":syfosm-common-metrics"))

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutinesVersion")

    implementation("io.ktor:ktor-client-cio:$ktorVersion")

    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    implementation("net.logstash.logback:logstash-logback-encoder:$logstashEncoderVersion")
}
