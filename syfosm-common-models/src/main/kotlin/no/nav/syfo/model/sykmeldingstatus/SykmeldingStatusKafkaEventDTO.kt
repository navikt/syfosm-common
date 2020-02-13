package no.nav.syfo.model.sykmeldingstatus

import java.time.LocalDateTime

data class SykmeldingStatusKafkaEventDTO(
    val sykmeldingId: String,
    val timestamp: LocalDateTime,
    val statusEvent: StatusEventDTO,
    val arbeidsgiver: ArbeidsgiverStatusDTO? = null,
    val sporsmals: List<SporsmalOgSvarDTO>? = null
)
