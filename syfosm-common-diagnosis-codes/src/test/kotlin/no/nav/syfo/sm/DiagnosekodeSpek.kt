package no.nav.syfo.sm

import org.amshove.kluent.shouldBeGreaterThan
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object DiagnosekodeSpek : Spek({
    describe("ICPC2") {
        it("Should be loaded") {
            Diagnosekoder.icpc2.size shouldBeGreaterThan 100
        }
    }

    describe("ICD10") {
        it("Should be loaded") {
            Diagnosekoder.icd10.size shouldBeGreaterThan 100
        }
    }
})
