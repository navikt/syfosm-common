package no.nav.syfo.helpers

import io.kotest.core.spec.style.FunSpec
import org.amshove.kluent.shouldBeEqualTo
import java.io.IOException

class NetworkHelpersSpek : FunSpec({
    context("Exception-cause crawler") {
        test("Should find a IOException in a nested Exception") {
            isCausedBy(
                Exception(IOException("Connection timed out")),
                3,
                arrayOf(IOException::class)
            ) shouldBeEqualTo true
        }
        test("Should find not a IOException in a nested Exception") {
            isCausedBy(
                Exception(IOException("Connection timed out")),
                3,
                arrayOf(RuntimeException::class)
            ) shouldBeEqualTo false
        }
        test("Should not find a IOException whenever the cause stack is too deep") {
            isCausedBy(
                Exception(Exception(Exception(IOException("Connection timed out")))),
                3,
                arrayOf(IOException::class)
            ) shouldBeEqualTo false
        }
        test("Should find a IOException whenever the cause stack is 3 deep") {
            isCausedBy(
                Exception(Exception(IOException("Connection timed out"))),
                3,
                arrayOf(IOException::class)
            ) shouldBeEqualTo true
        }
    }
})
