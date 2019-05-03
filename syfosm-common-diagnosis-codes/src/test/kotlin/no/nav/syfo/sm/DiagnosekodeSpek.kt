package no.nav.syfo.sm

import org.amshove.kluent.shouldBeEmpty
import org.amshove.kluent.shouldBeGreaterThan
import org.amshove.kluent.shouldContainAll
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe

object DiagnosekodeSpek : Spek({
    describe("ICPC2") {
        it("Should be loaded") {
            Diagnosekoder.icpc2.size shouldBeGreaterThan 100
        }

        it("Should map from ICPC2 to ICD10") {
            Diagnosekoder.icpc2["D74"]!!.toICD10() shouldContainAll listOf(Diagnosekoder.icd10["C169"]!!)
        }
    }

    describe("ICD10") {
        it("Should be loaded") {
            Diagnosekoder.icd10.size shouldBeGreaterThan 100
        }

        it("Should map from ICPC2 to ICD10") {
            Diagnosekoder.icd10["B24"]!!.toICPC2() shouldContainAll listOf(Diagnosekoder.icpc2["B90"]!!)
        }

        it("A ICD10 diagnosis code with multiple ICPC2 counterparts should return all of them") {
            Diagnosekoder.icd10["G98"]!!.toICPC2() shouldContainAll listOf(Diagnosekoder.icpc2["N18"]!!, Diagnosekoder.icpc2["N99"]!!)
        }

        it("A ICD10 without any ICPC2 counterparts should result in a empty list") {
            Diagnosekoder.icd10["A848"]!!.toICPC2().shouldBeEmpty()
        }
    }
})
