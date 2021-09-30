package no.nav.syfo.model.sykmelding.arbeidsgiver

import no.nav.syfo.model.Merknad
import no.nav.syfo.model.sykmelding.model.ArbeidsgiverDTO
import java.time.LocalDate
import java.time.OffsetDateTime

data class ArbeidsgiverSykmelding(
    val id: String,
    val mottattTidspunkt: OffsetDateTime,
    val syketilfelleStartDato: LocalDate?,
    val behandletTidspunkt: OffsetDateTime,
    val navnFastlege: String?,
    val arbeidsgiver: ArbeidsgiverDTO,
    val sykmeldingsperioder: List<SykmeldingsperiodeAGDTO>,
    val prognose: PrognoseAGDTO?,
    val tiltakArbeidsplassen: String?,
    val meldingTilArbeidsgiver: String?,
    val kontaktMedPasient: KontaktMedPasientAGDTO,
    val behandler: BehandlerAGDTO,
    val egenmeldt: Boolean,
    val papirsykmelding: Boolean,
    val harRedusertArbeidsgiverperiode: Boolean,
    val merknader: List<Merknad>?
)
