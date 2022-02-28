package no.nav.syfo.model

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.kotest.core.spec.style.FunSpec
import org.amshove.kluent.shouldBeEqualTo

class ValidationResultSpek : FunSpec({
    val objectMapper = ObjectMapper()
        .registerKotlinModule()
        .registerModule(JavaTimeModule())
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)

    context("Validering av ValidationResult") {
        test("Gyldig ValidationResult med resultat OK skal validere ok") {
            val validationResult: ValidationResult = objectMapper.readValue(
                ValidationResult::class.java.getResourceAsStream("/gyldig-validationresult-ok.json").readBytes()
                    .toString(Charsets.UTF_8)
            )

            validationResult.status shouldBeEqualTo Status.OK
            validationResult.ruleHits.size shouldBeEqualTo 0
        }

        test("Gyldig ValidationResult med resultat MANUAL_PROCESSING skal validere ok") {
            val validationResult: ValidationResult = objectMapper.readValue(
                ValidationResult::class.java.getResourceAsStream("/gyldig-validationresult-manuell.json").readBytes()
                    .toString(Charsets.UTF_8)
            )

            validationResult.status shouldBeEqualTo Status.MANUAL_PROCESSING
            validationResult.ruleHits.size shouldBeEqualTo 1
            validationResult.ruleHits[0] shouldBeEqualTo RuleInfo(
                ruleName = "TILBAKEDATERT_MED_BEGRUNNELSE_FORLENGELSE",
                messageForSender = "Sykmeldingen er tilbakedatert og felt 11.2 (begrunnelseIkkeKontakt) er utfylt",
                messageForUser = "Sykmeldingen er tilbakedatert og årsak for tilbakedatering er angitt",
                ruleStatus = Status.MANUAL_PROCESSING
            )
        }

        test("Gyldig ValidationResult med resultat INVALID skal validere ok") {
            val validationResult: ValidationResult = objectMapper.readValue(
                ValidationResult::class.java.getResourceAsStream("/gyldig-validationresult-avvist.json").readBytes()
                    .toString(Charsets.UTF_8)
            )

            validationResult.status shouldBeEqualTo Status.INVALID
            validationResult.ruleHits.size shouldBeEqualTo 1
            validationResult.ruleHits[0] shouldBeEqualTo RuleInfo(
                ruleName = "TILBAKEDATERT_MER_ENN_8_DAGER_FORSTE_SYKMELDING",
                messageForSender = "Sykmeldingen kan ikke rettes, det må skrives en ny. Pasienten har fått beskjed om å vente på ny sykmelding fra deg.",
                messageForUser = "Sykmeldingen er tilbakedatert uten begrunnelse fra den som sykmeldte deg.",
                ruleStatus = Status.INVALID
            )
        }
    }
})
