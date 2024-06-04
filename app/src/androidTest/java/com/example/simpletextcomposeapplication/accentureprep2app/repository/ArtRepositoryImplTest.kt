package com.example.simpletextcomposeapplication.accentureprep2app.repository

import androidx.test.platform.app.InstrumentationRegistry
import com.example.simpletextcomposeapplication.accentureprep2app.data.db.ArtDao2
import com.example.simpletextcomposeapplication.accentureprep2app.data.db.ArtDatabase
import com.example.simpletextcomposeapplication.accentureprep2app.data.remote.ArtDataResponse
import com.example.simpletextcomposeapplication.accentureprep2app.data.remote.ArtItemResponse
import com.example.simpletextcomposeapplication.accentureprep2app.data.remote.ArtService2
import com.example.simpletextcomposeapplication.accentureprep2app.domain.ArtItem
import com.google.gson.Gson
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.io.IOException

class ArtRepositoryImplTest {

    private lateinit var artRepository: ArtRepository
    private lateinit var mockWebServer: MockWebServer
    private lateinit var artService: ArtService2
    private lateinit var artDao: ArtDao2

    @Before
    @Throws(IOException::class)
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        artService = ArtService2Fake(mockWebServer)

        val context = InstrumentationRegistry.getInstrumentation().targetContext
        artDao = ArtDatabase.createDbForTesting(context).artDao

        artRepository = ArtRepositoryImpl(artService, artDao)
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

        val actualArts: List<ArtItem> = artRepository.getArtList()

        assertEquals(
            actualArts,
            listOf(
                expectedResponse.data[0].toArtItem(),
                expectedResponse.data[1].toArtItem()
            )
        )
    }

}

private fun ArtItemResponse.toArtItem(): ArtItem = ArtItem(
    id = this.id,
    title = this.title
)
