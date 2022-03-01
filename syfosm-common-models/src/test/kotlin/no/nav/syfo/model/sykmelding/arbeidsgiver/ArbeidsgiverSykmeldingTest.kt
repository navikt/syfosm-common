package no.nav.syfo.model.sykmelding.arbeidsgiver

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.kotest.core.spec.style.FunSpec
import org.amshove.kluent.shouldBeEqualTo
import java.time.LocalDate
import java.time.Month

class ArbeidsgiverSykmeldingTest : FunSpec({
    val objectMapper = ObjectMapper()
        .registerKotlinModule()
        .registerModule(JavaTimeModule())
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true)
        .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)

    context("Validering av ArbeidsgiverSykmelding") {
        test("Maksimal gyldig ArbeidsgiverSykmelding skal validere ok") {
            val arbeidsgiverSykmelding: ArbeidsgiverSykmelding = objectMapper.readValue(
                ArbeidsgiverSykmelding::class.java.getResourceAsStream("/gyldig-arbeidsgiversykmelding-maksimal.json")
                    .readBytes().toString(Charsets.UTF_8)
            )

            arbeidsgiverSykmelding.mottattTidspunkt.toLocalDate() shouldBeEqualTo LocalDate.of(2020, Month.JANUARY, 1)
            arbeidsgiverSykmelding.id shouldBeEqualTo "b5e84795-43a8-4adb-ac1d-4b2e1e0c5fb7"
        }
        test("Minimal gyldig ArbeidsgiverSykmelding skal validere ok") {
            val arbeidsgiverSykmelding: ArbeidsgiverSykmelding = objectMapper.readValue(
                ArbeidsgiverSykmelding::class.java.getResourceAsStream("/gyldig-arbeidsgiversykmelding-minimal.json")
                    .readBytes().toString(Charsets.UTF_8)
            )

            arbeidsgiverSykmelding.mottattTidspunkt.toLocalDate() shouldBeEqualTo LocalDate.of(2020, Month.JANUARY, 1)
            arbeidsgiverSykmelding.id shouldBeEqualTo "b5e84795-43a8-4adb-ac1d-4b2e1e0c5fb7"
            arbeidsgiverSykmelding.arbeidsgiver shouldBeEqualTo ArbeidsgiverAGDTO(null, null)
            arbeidsgiverSykmelding.prognose shouldBeEqualTo null
        }
    }
})
