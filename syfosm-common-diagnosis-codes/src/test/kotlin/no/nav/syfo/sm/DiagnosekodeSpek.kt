package no.nav.syfo.sm

import io.kotest.core.spec.style.FunSpec
import org.amshove.kluent.shouldBeGreaterThan

class DiagnosekodeSpek : FunSpec({
    context("ICPC2") {
        test("Should be loaded") {
            Diagnosekoder.icpc2.size shouldBeGreaterThan 100
        }
    }

    context("ICD10") {
        test("Should be loaded") {
            Diagnosekoder.icd10.size shouldBeGreaterThan 100
        }
    }
})
