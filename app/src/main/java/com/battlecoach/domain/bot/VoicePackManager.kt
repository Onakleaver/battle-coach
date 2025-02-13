package com.battlecoach.domain.bot

import android.content.Context
import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VoicePackManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val storage: FirebaseStorage
) {
    private val voicePacksDir = File(context.filesDir, "voice_packs")

    init {
        voicePacksDir.mkdirs()
    }

    fun isVoicePackInstalled(packId: String): Boolean {
        return File(voicePacksDir, packId).exists()
    }

    suspend fun downloadVoicePack(packId: String): Result<Unit> = withContext(Dispatchers.IO) {
        try {
            val packDir = File(voicePacksDir, packId)
            packDir.mkdirs()

            // Download manifest first
            val manifestRef = storage.reference
                .child("voice_packs/$packId/manifest.json")
            val manifestFile = File(packDir, "manifest.json")
            manifestRef.getFile(manifestFile).await()

            // Parse manifest and download all voice files
            val manifest = parseManifest(manifestFile)
            manifest.files.forEach { fileName ->
                val fileRef = storage.reference
                    .child("voice_packs/$packId/files/$fileName")
                val localFile = File(packDir, fileName)
                fileRef.getFile(localFile).await()
            }

            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun getVoiceUri(packId: String, resourceId: String): Uri {
        return Uri.fromFile(File(voicePacksDir, "$packId/files/$resourceId.mp3"))
    }

    private fun parseManifest(file: File): VoicePackManifest {
        // Implementation to parse JSON manifest
        return VoicePackManifest(emptyList()) // Placeholder
    }
}

data class VoicePackManifest(
    val files: List<String>
) 