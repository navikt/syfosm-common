package no.nav.syfo.model.sykmeldingstatus

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.readValue
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.amshove.kluent.shouldEqual
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.time.LocalDate
import java.time.Month

object SykmeldingstatusSpek : Spek({
    val objectMapper = ObjectMapper()
        .registerKotlinModule()
        .registerModule(JavaTimeModule())
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)

    describe("Validering av SykmeldingStatusKafkaMessage") {
        it("Maksimal gyldig SykmeldingStatusKafkaMessage med status SENDT skal validere ok") {
            val sykmeldingStatusKafkaMessageDTO: SykmeldingStatusKafkaMessageDTO = objectMapper.readValue(SykmeldingStatusKafkaMessageDTO::class.java.getResourceAsStream("/gyldig-sykmeldingstatus-maksimal-sendt.json").readBytes().toString(Charsets.UTF_8))

            sykmeldingStatusKafkaMessageDTO.kafkaMetadata.sykmeldingId shouldEqual "7f5bc53b-8b99-4f6a-a4c8-033df15050d0"
            sykmeldingStatusKafkaMessageDTO.kafkaMetadata.source shouldEqual "syfoservice"
            sykmeldingStatusKafkaMessageDTO.kafkaMetadata.timestamp.toLocalDate() shouldEqual LocalDate.of(2020, Month.JANUARY, 23)
            sykmeldingStatusKafkaMessageDTO.kafkaMetadata.fnr shouldEqual "12345678910"

            sykmeldingStatusKafkaMessageDTO.event.sykmeldingId shouldEqual "7f5bc53b-8b99-4f6a-a4c8-033df15050d0"
            sykmeldingStatusKafkaMessageDTO.event.timestamp.toLocalDate() shouldEqual LocalDate.of(2020, Month.JANUARY, 23)
            sykmeldingStatusKafkaMessageDTO.event.statusEvent shouldEqual STATUS_SENDT
            sykmeldingStatusKafkaMessageDTO.event.arbeidsgiver shouldEqual ArbeidsgiverStatusDTO(orgnummer = "979797979", juridiskOrgnummer = "898989898", orgNavn = "LØNNS- OG REGNSKAPSSENTERET")
            sykmeldingStatusKafkaMessageDTO.event.sporsmals shouldEqual listOf(
                SporsmalOgSvarDTO(tekst = "Jeg er sykmeldt fra", shortName = ShortNameDTO.ARBEIDSSITUASJON, svartype = SvartypeDTO.ARBEIDSSITUASJON, svar = "ARBEIDSTAKER"),
                SporsmalOgSvarDTO(tekst = "Skal finne ny nærmeste leder", shortName = ShortNameDTO.NY_NARMESTE_LEDER, svartype = SvartypeDTO.JA_NEI, svar = "JA"))
        }

        it("Maksimal gyldig SykmeldingStatusKafkaMessage med status BEKREFTET skal validere ok") {
            val sykmeldingStatusKafkaMessageDTO: SykmeldingStatusKafkaMessageDTO = objectMapper.readValue(SykmeldingStatusKafkaMessageDTO::class.java.getResourceAsStream("/gyldig-sykmeldingstatus-maksimal-bekreftet.json").readBytes().toString(Charsets.UTF_8))

            sykmeldingStatusKafkaMessageDTO.kafkaMetadata.sykmeldingId shouldEqual "b5e84795-43a8-4adb-ac1d-4b2e1e0c5fb7"
            sykmeldingStatusKafkaMessageDTO.kafkaMetadata.source shouldEqual "syfoservice"
            sykmeldingStatusKafkaMessageDTO.kafkaMetadata.timestamp.toLocalDate() shouldEqual LocalDate.of(2020, Month.JANUARY, 23)
            sykmeldingStatusKafkaMessageDTO.kafkaMetadata.fnr shouldEqual "12345678910"

            sykmeldingStatusKafkaMessageDTO.event.sykmeldingId shouldEqual "b5e84795-43a8-4adb-ac1d-4b2e1e0c5fb7"
            sykmeldingStatusKafkaMessageDTO.event.timestamp.toLocalDate() shouldEqual LocalDate.of(2020, Month.JANUARY, 23)
            sykmeldingStatusKafkaMessageDTO.event.statusEvent shouldEqual STATUS_BEKREFTET
            sykmeldingStatusKafkaMessageDTO.event.arbeidsgiver shouldEqual null
            sykmeldingStatusKafkaMessageDTO.event.sporsmals!!.size shouldEqual 4
            sykmeldingStatusKafkaMessageDTO.event.sporsmals shouldEqual listOf(
                SporsmalOgSvarDTO(tekst = "Jeg er sykmeldt fra", shortName = ShortNameDTO.ARBEIDSSITUASJON, svartype = SvartypeDTO.ARBEIDSSITUASJON, svar = "FRILANSER"),
                SporsmalOgSvarDTO(tekst = "Har du forsikring som gjelder de første 16 dagene av sykefraværet?", shortName = ShortNameDTO.FORSIKRING, svartype = SvartypeDTO.JA_NEI, svar = "JA"),
                SporsmalOgSvarDTO(tekst = "Brukte du egenmelding eller noen annen sykmelding før datoen denne sykmeldingen gjelder fra?", shortName = ShortNameDTO.FRAVAER, svartype = SvartypeDTO.JA_NEI, svar = "JA"),
                SporsmalOgSvarDTO(tekst = "Hvilke dager var du borte fra jobb før datoen sykmeldingen gjelder fra?", shortName = ShortNameDTO.PERIODE, svartype = SvartypeDTO.PERIODER, svar = "{[{\"fom\": \"2019-8-1\", \"tom\": \"2019-8-15\"}, {\"fom\": \"2019-9-1\", \"tom\": \"2019-9-3\"}]}"))
        }

        it("Minimal gyldig SykmeldingStatusKafkaMessage skal validere ok") {
            val sykmeldingStatusKafkaMessageDTO: SykmeldingStatusKafkaMessageDTO = objectMapper.readValue(SykmeldingStatusKafkaMessageDTO::class.java.getResourceAsStream("/gyldig-sykmeldingstatus-minimal.json").readBytes().toString(Charsets.UTF_8))

            sykmeldingStatusKafkaMessageDTO.kafkaMetadata.sykmeldingId shouldEqual "7f5bc53b-8b99-4f6a-a4c8-033df15050d0"
            sykmeldingStatusKafkaMessageDTO.kafkaMetadata.source shouldEqual "syfoservice"
            sykmeldingStatusKafkaMessageDTO.kafkaMetadata.timestamp.toLocalDate() shouldEqual LocalDate.of(2020, Month.JANUARY, 23)
            sykmeldingStatusKafkaMessageDTO.kafkaMetadata.fnr shouldEqual "12345678910"

            sykmeldingStatusKafkaMessageDTO.event.sykmeldingId shouldEqual "7f5bc53b-8b99-4f6a-a4c8-033df15050d0"
            sykmeldingStatusKafkaMessageDTO.event.timestamp.toLocalDate() shouldEqual LocalDate.of(2020, Month.JANUARY, 23)
            sykmeldingStatusKafkaMessageDTO.event.statusEvent shouldEqual STATUS_SENDT
            sykmeldingStatusKafkaMessageDTO.event.arbeidsgiver shouldEqual null
            sykmeldingStatusKafkaMessageDTO.event.sporsmals shouldEqual null
        }
    }
})
