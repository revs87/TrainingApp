package com.example.simpletextcomposeapplication.accentureprep2app.data.remote

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class ArtService2ImplTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var artService: ArtApi2


    @Before
    @Throws(IOException::class)
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        val gson = GsonBuilder().create()

        artService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ArtApi2::class.java)
    }
    @After
    @Throws(IOException::class)
    fun tearDown() { mockWebServer.shutdown() }


    @Test
    fun testGetArtList() = runBlocking {

        val expectedResponse = ArtDataResponse(
            data = listOf(
                ArtItemResponse(1, "Mona Lisa"),
                ArtItemResponse(2, "The Starry Night")
            )
        )
        val json = Gson().toJson(expectedResponse)
        mockWebServer.enqueue(MockResponse().setBody(json))

        val actualResponse: List<ArtItemResponse> = artService.getArtList().data

        assertEquals(actualResponse, expectedResponse.data)
    }

}