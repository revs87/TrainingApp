package com.example.simpletextcomposeapplication.core

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.IvParameterSpec

object Crypto {
    private const val KEY_ALIAS = "secret"
    private const val ALGORITHM = KeyProperties.KEY_ALGORITHM_AES
    private const val BLOCK_MODE = KeyProperties.BLOCK_MODE_CBC
    private const val PADDING = KeyProperties.ENCRYPTION_PADDING_PKCS7
    private const val TRANSFORMATION = "$ALGORITHM/$BLOCK_MODE/$PADDING"

    private val cypher = Cipher.getInstance(TRANSFORMATION)
    private val keyStore = KeyStore.getInstance("AndroidKeyStore").apply { load(null) }

    private fun getSecretKey(): SecretKey {
        val existingKey = keyStore.getEntry(KEY_ALIAS, null) as? KeyStore.SecretKeyEntry
        return existingKey?.secretKey ?: createSecretKey()
    }

    private fun createSecretKey(): SecretKey =
        KeyGenerator
            .getInstance(ALGORITHM)
            .apply {
                init(
                    KeyGenParameterSpec.Builder(
                        KEY_ALIAS,
                        KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                    )
                        .setBlockModes(BLOCK_MODE)
                        .setEncryptionPaddings(PADDING)
                        .setRandomizedEncryptionRequired(true)
                        .setUserAuthenticationRequired(false)
                        .build()
                )
            }
            .generateKey()

    fun encrypt(bytes: ByteArray): ByteArray {
        cypher.init(Cipher.ENCRYPT_MODE, getSecretKey())
        val iv = cypher.iv
        val encrypted = cypher.doFinal(bytes)
        return iv + encrypted
    }

    fun decrypt(bytes: ByteArray): ByteArray {
        val iv = bytes.copyOfRange(0, cypher.blockSize)
        val data = bytes.copyOfRange(cypher.blockSize, bytes.size)
        cypher.init(Cipher.DECRYPT_MODE, getSecretKey(), IvParameterSpec(iv))
        return cypher.doFinal(data)
    }
}