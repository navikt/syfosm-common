package no.nav.syfo.model.juridisk

import java.time.LocalDate

data class JuridiskVurdering(
    private val id: String,
    private val eventName: String,
    private val version: String,
    private val kilde: String,
    private val versjonAvKode: String,
    private val fodselsnummer: String,
    private val juridiskHenvisning: JuridiskHenvisning,
    private val sporing: Map<String, String>,
    private val input: Map<String, Any>,
    private val utfall: JuridiskUtfall
)

data class JuridiskHenvisning(
    private val lovverk: String,
    private val lovverksversjon: LocalDate,
    private val paragraf: String,
    private val ledd: Int?,
    private val puktum: String?,
    private val bokstav: String?
)

enum class JuridiskUtfall {
    VILKAR_OPPFYLT,
    VILKAR_IKKE_OPPFYLT,
    VILKAR_UAVKLART,
    VILKAR_BEREGNET
}