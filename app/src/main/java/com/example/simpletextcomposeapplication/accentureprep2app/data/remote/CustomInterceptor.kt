package com.example.simpletextcomposeapplication.accentureprep2app.data.remote

import com.example.simpletextcomposeapplication.di.LOGGER_ENTRY_MAX_LEN
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okio.Buffer
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets


class CustomInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        println("INTERCEPTOR request: " + getRequestBody(request))

        val response = chain.proceed(request)
        print(response)
        return response
    }

    private fun print(response: Response) {
        val json = getResponseBody(response).toString()
        (0..(json.length / LOGGER_ENTRY_MAX_LEN)).forEach {
            val endIndex = (it * LOGGER_ENTRY_MAX_LEN) + LOGGER_ENTRY_MAX_LEN
            val allowedEndIndex =
                if (endIndex > json.length) json.length - 1
                else endIndex - 1
            val sub = json.substring(
                IntRange(
                    start = (it * LOGGER_ENTRY_MAX_LEN),
                    endInclusive = allowedEndIndex
                )
            )
            println(sub)
        }
    }
}

private fun getResponseBody(response: Response): String? {
    val responseBody = response.body
    val source = responseBody?.source()
    source?.request(Long.MAX_VALUE) // Buffer the entire body.
    val buffer = source?.buffer
    val contentType = responseBody?.contentType()
    val charset: Charset =
        contentType?.charset(StandardCharsets.UTF_8) ?: StandardCharsets.UTF_8
    return buffer?.clone()?.readString(charset)
}

private fun getRequestBody(request: Request): String {
    val requestBody = request.body
    val buffer = Buffer()
    requestBody?.writeTo(buffer)
    val contentType = requestBody?.contentType()
    val charset: Charset =
        contentType?.charset(StandardCharsets.UTF_8) ?: StandardCharsets.UTF_8
    return buffer.readString(charset)
}