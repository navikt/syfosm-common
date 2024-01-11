package no.nav.syfo.helpers

import org.amshove.kluent.shouldBeEqualTo
import org.junit.jupiter.api.Test
import java.io.IOException

internal class NetworkHelpersTest {
    @Test
    fun `Should find a IOException in a nested Exception`() {
        isCausedBy(
            Exception(IOException("Connection timed out")),
            3,
            arrayOf(IOException::class)
        ) shouldBeEqualTo true
    }

    @Test
    fun `Should find not a IOException in a nested Exception`() {
        isCausedBy(
            Exception(IOException("Connection timed out")),
            3,
            arrayOf(RuntimeException::class)
        ) shouldBeEqualTo false
    }

    @Test
    fun `Should not find a IOException whenever the cause stack is too deep`() {
        isCausedBy(
            Exception(Exception(Exception(IOException("Connection timed out")))),
            3,
            arrayOf(IOException::class)
        ) shouldBeEqualTo false
    }

    @Test
    fun `Should find a IOException whenever the cause stack is 3 deep`() {
        isCausedBy(
            Exception(Exception(IOException("Connection timed out"))),
            3,
            arrayOf(IOException::class)
        ) shouldBeEqualTo true
    }
}
