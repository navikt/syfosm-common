package no.nav.syfo.model

import no.nav.syfo.model.juridisk.JuridiskVurdering

data class ValidationResult(
    val status: Status,
    val ruleHits: List<RuleInfo>,
    val juridiskeVurderinger: List<JuridiskVurdering>?
)

data class RuleInfo(
    val ruleName: String,
    val messageForSender: String,
    val messageForUser: String,
    val ruleStatus: Status
)

enum class Status {
    OK,
    MANUAL_PROCESSING,
    INVALID
}
