package no.nav.syfo.helpers

import org.amshove.kluent.shouldEqual
import org.spekframework.spek2.Spek
import org.spekframework.spek2.style.specification.describe
import java.io.IOException

object NetworkHelpersSpek : Spek({
    describe("Exception-cause crawler") {
        it("Should find a IOException in a nested Exception") {
            isCausedBy(Exception(IOException("Connection timed out")), 3, arrayOf(IOException::class)) shouldEqual true
        }
        it("Should find not a IOException in a nested Exception") {
            isCausedBy(
                Exception(IOException("Connection timed out")),
                3,
                arrayOf(RuntimeException::class)
            ) shouldEqual false
        }
        it("Should not find a IOException whenever the cause stack is too deep") {
            isCausedBy(
                Exception(Exception(Exception(IOException("Connection timed out")))),
                3,
                arrayOf(IOException::class)
            ) shouldEqual false
        }
        it("Should find a IOException whenever the cause stack is 3 deep") {
            isCausedBy(
                Exception(Exception(IOException("Connection timed out"))),
                3,
                arrayOf(IOException::class)
            ) shouldEqual true
        }
    }
})
