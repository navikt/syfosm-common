package no.nav.syfo.model.sykmeldingstatus

import java.time.OffsetDateTime

data class KafkaMetadataDTO(
    val sykmeldingId: String,
    val timestamp: OffsetDateTime,
    val source: String
)
