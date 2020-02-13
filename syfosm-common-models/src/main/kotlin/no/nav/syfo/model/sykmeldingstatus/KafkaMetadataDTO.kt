package no.nav.syfo.model.sykmeldingstatus

import java.time.LocalDateTime

data class KafkaMetadataDTO(
    val sykmeldingId: String,
    val timestamp: LocalDateTime,
    val source: String
)
