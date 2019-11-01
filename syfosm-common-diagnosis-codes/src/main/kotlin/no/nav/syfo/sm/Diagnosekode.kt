package no.nav.syfo.sm

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import no.nav.syfo.model.Diagnose
import java.io.InputStream
import kotlin.reflect.KClass

object Diagnosekoder {
    val objectMapper = ObjectMapper().apply {
        registerKotlinModule()
        registerModule(JavaTimeModule())
        configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true)
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    }
    const val ICPC2_CODE = "2.16.578.1.12.4.1.1.7170"
    const val ICD10_CODE = "2.16.578.1.12.4.1.1.7110"
    const val ICPC2_INFOTRYGD_CODE = "5"
    const val ICD10_INFOTRYGD_CODE = "3"

    interface DiagnosekodeType {
        val code: String
        val text: String
        val mapsTo: List<String>
        val oid: String
        val infotrygdCode: String
    }

    data class ICPC2(
        override val code: String,
        override val text: String,
        override val mapsTo: List<String>,
        override val oid: String = ICPC2_CODE,
        override val infotrygdCode: String = ICPC2_INFOTRYGD_CODE
    ) : DiagnosekodeType {
        fun toICD10(): List<ICD10> = mapsTo.mapNotNull { icd10[it] }
    }

    data class ICD10(
        override val code: String,
        override val text: String,
        override val mapsTo: List<String>,
        override val oid: String = ICD10_CODE,
        override val infotrygdCode: String = ICD10_INFOTRYGD_CODE
    ) : DiagnosekodeType {
        fun toICPC2(): List<ICPC2> = mapsTo.mapNotNull { icpc2[it] }
    }

    val icd10 = loadCodes(Diagnosekoder::class.java.getResourceAsStream("/icd10.json"), Array<ICD10>::class)
    val icpc2 = loadCodes(Diagnosekoder::class.java.getResourceAsStream("/icpc2.json"), Array<ICPC2>::class)

    inline fun <reified T : DiagnosekodeType> loadCodes(jsonResource: InputStream, inputType: KClass<Array<T>>) =
        objectMapper.readValue(jsonResource, inputType.java)
            .map { it.code to it }
            .toMap()

    operator fun contains(oid: String?) = oid == ICPC2_CODE || oid == ICD10_CODE
}

fun Diagnose.isICPC2(): Boolean = system == Diagnosekoder.ICPC2_CODE

fun Diagnose.toICPC2() = if (isICPC2()) {
     listOf(Diagnosekoder.icpc2[kode])
} else {
    Diagnosekoder.icd10[kode]?.toICPC2() ?: listOf()
}
