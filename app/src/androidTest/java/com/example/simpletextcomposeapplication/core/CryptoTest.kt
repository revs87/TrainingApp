package com.example.simpletextcomposeapplication.core

import com.google.common.truth.Truth.assertThat
import org.junit.Test


class CryptoTest {

    @Test
    fun encryptAndDecrypt() {
        val expected = "Hello World"

        val encrypted = Crypto.encrypt(expected.toByteArray())
        val decrypted = Crypto.decrypt(encrypted).decodeToString()

        assertThat(decrypted).isEqualTo(expected)
    }

    @Test
    fun encryptAndDecryptTwice_verifyDifferentEncryptedValues() {
        val expected = "Hello World"

        val encrypted1 = Crypto.encrypt(expected.toByteArray())
        val decrypted1 = Crypto.decrypt(encrypted1).decodeToString()

        val encrypted2 = Crypto.encrypt(decrypted1.toByteArray())
        val decrypted2 = Crypto.decrypt(encrypted2).decodeToString()

        assertThat(decrypted1).isEqualTo(expected)
        assertThat(decrypted2).isEqualTo(expected)
        assertThat(encrypted1.decodeToString()).isNotEqualTo(encrypted2.decodeToString())
    }

}