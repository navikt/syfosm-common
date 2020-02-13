package no.nav.syfo.model.sykmeldingstatus

import java.time.ZonedDateTime

data class KafkaMetadataDTO(
    val sykmeldingId: String,
    val timestamp: ZonedDateTime,
    val source: String
)
