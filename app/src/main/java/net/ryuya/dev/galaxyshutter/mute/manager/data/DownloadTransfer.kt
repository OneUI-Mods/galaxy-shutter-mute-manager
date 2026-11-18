package net.ryuya.dev.galaxyshutter.mute.manager.data

import java.io.File
import java.io.IOException
import java.io.InputStream

/** Reject incomplete bodies and delete partial output, leaving the previous APK untouched. */
internal object DownloadTransfer {
    private const val MAX_BYTES = 512L * 1024 * 1024

    fun write(
        input: InputStream,
        pending: File,
        expected: Long,
        progress: (Float) -> Unit,
        checkActive: () -> Unit,
    ) {
        var complete = false
        try {
            if (expected > MAX_BYTES) throw IOException("Download too large")
            var total = 0L
            pending.outputStream().use { output ->
                val buffer = ByteArray(8192)
                while (true) {
                    checkActive()
                    val count = input.read(buffer)
                    if (count < 0) break
                    total += count
                    if (total > MAX_BYTES || expected >= 0 && total > expected)
                        throw IOException("Unexpected download size")
                    output.write(buffer, 0, count)
                    if (expected > 0) progress(total.toFloat() / expected)
                }
            }
            checkActive()
            if (expected >= 0 && total != expected) throw IOException("Incomplete download")
            complete = true
        } finally {
            if (!complete) pending.delete()
        }
    }
}
