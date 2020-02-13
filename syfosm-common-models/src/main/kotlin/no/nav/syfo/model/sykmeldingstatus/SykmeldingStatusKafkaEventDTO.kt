package no.nav.syfo.model.sykmeldingstatus

import java.time.ZonedDateTime

data class SykmeldingStatusKafkaEventDTO(
    val sykmeldingId: String,
    val timestamp: ZonedDateTime,
    val statusEvent: StatusEventDTO,
    val arbeidsgiver: ArbeidsgiverStatusDTO? = null,
    val sporsmals: List<SporsmalOgSvarDTO>? = null
)
