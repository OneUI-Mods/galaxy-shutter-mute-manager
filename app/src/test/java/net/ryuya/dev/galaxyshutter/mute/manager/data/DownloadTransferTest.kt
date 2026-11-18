package net.ryuya.dev.galaxyshutter.mute.manager.data

import java.io.ByteArrayInputStream
import java.io.File
import java.io.IOException
import java.io.InterruptedIOException
import org.junit.Assert.*
import org.junit.Test

class DownloadTransferTest {
    @Test
    fun truncatedBodiesDoNotLeaveAnInstallablePartialFile() {
        val pending = File.createTempFile("download-test", ".part")
        try {
            DownloadTransfer.write(ByteArrayInputStream(byteArrayOf(1, 2)), pending, 100, {}, {})
            fail("Incomplete body required")
        } catch (_: IOException) {}
        assertFalse(pending.exists())
    }

    @Test
    fun cancellationDeletesThePartialFileAndPropagates() {
        val pending = File.createTempFile("download-test", ".part")
        try {
            DownloadTransfer.write(
                ByteArrayInputStream(ByteArray(20000)),
                pending,
                -1,
                {},
                { throw InterruptedIOException("cancelled") },
            )
            fail("Cancellation required")
        } catch (_: InterruptedIOException) {}
        assertFalse(pending.exists())
    }

    @Test
    fun completeUnknownLengthBodyCanBePublished() {
        val pending = File.createTempFile("download-test", ".part")
        val bytes = byteArrayOf(1, 2, 3)
        DownloadTransfer.write(ByteArrayInputStream(bytes), pending, -1, {}, {})
        assertArrayEquals(bytes, pending.readBytes())
        pending.delete()
    }
}
