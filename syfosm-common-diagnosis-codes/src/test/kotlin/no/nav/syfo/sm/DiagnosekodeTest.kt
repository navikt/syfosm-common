package no.nav.syfo.sm

import org.amshove.kluent.shouldBeGreaterThan
import org.junit.jupiter.api.Test

internal class DiagnosekodeTest {
    @Test
    fun `ICPC2 should be loaded`() {
        Diagnosekoder.icpc2.size shouldBeGreaterThan 100
    }


    @Test
    fun `ICD10 should be loaded`() {
        Diagnosekoder.icd10.size shouldBeGreaterThan 100
    }

}
