package no.nav.syfo.client

data class OidcToken(
    val access_token: String,
    val token_type: String,
    val expires_in: Long
)
