package no.nav.syfo.model

data class UtenlandskSykmelding(
    val land: String,
    @Deprecated("Not in use") val andreRelevanteOpplysninger: Boolean
)
