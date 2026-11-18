package net.ryuya.dev.galaxyshutter.mute.manager.data

import android.content.Context
import android.content.pm.PackageManager
import java.io.File
import java.net.HttpURLConnection
import java.net.URL
import java.nio.file.Files
import java.nio.file.StandardCopyOption
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import net.ryuya.dev.galaxyshutter.mute.manager.data.model.GitHubRelease
import net.ryuya.dev.galaxyshutter.mute.manager.data.network.RetrofitClient

/** GitHub Releases API の呼び出しと APK ダウンロードを担うリポジトリ */
class GitHubReleaseRepository(
    private val context: Context,
    private val preferencesRepository: PreferencesRepository,
) {

    /**
     * 設定された URL から最新リリース情報を取得する
     *
     * @return 成功時は GitHubRelease、失敗時は例外をスロー
     */
    suspend fun getLatestRelease(): GitHubRelease =
        withContext(Dispatchers.IO) {
            val url = preferencesRepository.releasesUrl.first()
            RetrofitClient.apiService.getLatestRelease(url)
        }

    /**
     * APK をダウンロードしてキャッシュディレクトリに保存する
     *
     * @param downloadUrl APK の直接ダウンロード URL
     * @param onProgress ダウンロード進捗コールバック（0.0〜1.0）
     * @return ダウンロードした APK ファイル（失敗時は null）
     */
    suspend fun downloadApk(
        downloadUrl: String,
        onProgress: (Float) -> Unit,
    ): File? =
        withContext(Dispatchers.IO) {
            var pending: File? = null
            var connection: HttpURLConnection? = null
            try {
                val active = currentCoroutineContext()
                active.ensureActive()
                val partial =
                    File.createTempFile("shutter-download-", ".part", context.cacheDir).also {
                        pending = it
                    }
                val destFile = File(context.cacheDir, "galaxy_shutter_mute.apk")
                val request =
                    (URL(downloadUrl).openConnection() as HttpURLConnection).also {
                        connection = it
                    }
                request.connectTimeout = 15_000
                request.readTimeout = 20_000
                if (request.responseCode != HttpURLConnection.HTTP_OK)
                    throw java.io.IOException("Download HTTP ${request.responseCode}")
                request.inputStream.use { input ->
                    DownloadTransfer.write(input, partial, request.contentLengthLong, onProgress) {
                        active.ensureActive()
                    }
                }
                Files.move(
                    partial.toPath(),
                    destFile.toPath(),
                    StandardCopyOption.ATOMIC_MOVE,
                    StandardCopyOption.REPLACE_EXISTING,
                )
                destFile
            } catch (cancelled: CancellationException) {
                throw cancelled
            } catch (_: Exception) {
                null
            } finally {
                connection?.disconnect()
                pending?.delete()
            }
        }

    /**
     * Galaxy Shutter Mute のインストール済みバージョン名を返す
     *
     * @return バージョン名（未インストールの場合は null）
     */
    fun getInstalledVersion(): String? {
        return try {
            context.packageManager
                .getPackageInfo(
                    "net.ryuya.dev.galaxyshutter.mute",
                    0,
                )
                .versionName
        } catch (e: PackageManager.NameNotFoundException) {
            null
        }
    }
}
