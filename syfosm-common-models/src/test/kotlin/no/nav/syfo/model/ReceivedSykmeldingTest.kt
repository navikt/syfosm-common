package no.nav.syfo.model

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.amshove.kluent.shouldBeEqualTo
import org.amshove.kluent.shouldNotBe
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import java.time.Month

internal class ReceivedSykmeldingTest {
    val objectMapper = ObjectMapper()
        .registerKotlinModule()
        .registerModule(JavaTimeModule())
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)

    @Test
    fun `Maksimal gyldig ReceivedSykmelding skal validere ok`() {
        val receivedSykmelding: ReceivedSykmelding = objectMapper.readValue(
            ReceivedSykmelding::class.java.getResourceAsStream("/gyldig-receivedsykmelding-maksimal.json")
                .readBytes().toString(Charsets.UTF_8)
        )

        receivedSykmelding.mottattDato shouldBeEqualTo LocalDateTime.of(2020, Month.JANUARY, 1, 12, 28, 40)
        receivedSykmelding.sykmelding.id shouldBeEqualTo "b5e84795-43a8-4adb-ac1d-4b2e1e0c5fb7"
        receivedSykmelding.sykmelding.arbeidsgiver shouldBeEqualTo Arbeidsgiver(
            harArbeidsgiver = HarArbeidsgiver.EN_ARBEIDSGIVER,
            navn = "Min Bedrift",
            yrkesbetegnelse = "Konsulent",
            stillingsprosent = 100
        )
        receivedSykmelding.sykmelding.utdypendeOpplysninger["6.3"] shouldNotBe null
    }

    @Test
    fun `Minimal gyldig ReceivedSykmelding skal validere ok`() {
        val receivedSykmelding: ReceivedSykmelding = objectMapper.readValue(
            ReceivedSykmelding::class.java.getResourceAsStream("/gyldig-receivedsykmelding-minimal.json")
                .readBytes().toString(Charsets.UTF_8)
        )

        receivedSykmelding.msgId shouldBeEqualTo "A24A4DD6-AE61-493B-A0C9-46FD45F859DB"
        receivedSykmelding.legekontorOrgNr shouldBeEqualTo null
        receivedSykmelding.sykmelding.behandler.etternavn shouldBeEqualTo "Superlege"
        receivedSykmelding.sykmelding.kontaktMedPasient.begrunnelseIkkeKontakt shouldBeEqualTo null
    }
}
