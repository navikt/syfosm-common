package no.nav.syfo.model.sykmelding.model

class SykmeldingStatusMetadataDTO(
    val forrigeStatus: String,
    val forrigeOrgnummer: String? = null,
    val forrigeSykmeldingsId: String,
)