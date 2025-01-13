package com.example.simpletextcomposeapplication.core

import androidx.datastore.core.Serializer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi


@Serializable
data class UserPreference(
    val data: String? = null
)

object UserPreferenceSerializer : Serializer<UserPreference> {
    override val defaultValue: UserPreference
        get() = UserPreference()

    @OptIn(ExperimentalEncodingApi::class)
    override suspend fun readFrom(input: InputStream): UserPreference {
        val encryptedBase64 = withContext(Dispatchers.IO) {
            input.use {
                it.readBytes()
            }
        }
        val encrypted = Base64.decode(encryptedBase64)
        val decrypted = Crypto.decrypt(encrypted)
        val json = decrypted.decodeToString()
        return Json.decodeFromString(json)
    }

    @OptIn(ExperimentalEncodingApi::class)
    override suspend fun writeTo(
        t: UserPreference,
        output: OutputStream
    ) {
        val json = Json.encodeToString(t)
        val bytes = json.toByteArray()
        val encrypted = Crypto.encrypt(bytes)
        val encryptedBase64 = Base64.encodeToByteArray(encrypted)
        withContext(Dispatchers.IO) {
            output.use {
                it.write(encryptedBase64)
            }
        }
    }
}