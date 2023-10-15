package com.example.simpletextcomposeapplication.accentureprep3app.data.repository

import com.example.simpletextcomposeapplication.accentureprep3app.data.db.ArtyDao
import com.example.simpletextcomposeapplication.accentureprep3app.data.db.ArtyEntity
import com.example.simpletextcomposeapplication.accentureprep3app.data.remote.ArtyApi
import com.example.simpletextcomposeapplication.accentureprep3app.data.remote.ArtyDetailedResponse
import com.example.simpletextcomposeapplication.accentureprep3app.data.remote.ArtyListResponse
import com.example.simpletextcomposeapplication.accentureprep3app.data.remote.ArtyResponse
import com.example.simpletextcomposeapplication.accentureprep3app.domain.ArtyItem
import com.example.simpletextcomposeapplication.accentureprep3app.domain.ArtyItemDetails
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

interface ArtyRepository {
    fun getArtyList(): Flow<List<ArtyItem>>
    suspend fun getArtyDetails(id: Long): ArtyItemDetails
}

class ArtyRepositoryImpl @Inject constructor(
    private val service: ArtyApi,
    private val dao: ArtyDao
) : ArtyRepository {
    @OptIn(DelicateCoroutinesApi::class)
    override fun getArtyList(): Flow<List<ArtyItem>> {
        GlobalScope.launch {
            val deferredResult: Deferred<ArtyListResponse> = async { service.getArtyList() }
            val result = deferredResult.await()
            result.data.forEach { artyResponse ->
                dao.insertArty(artyResponse.toEntity())
            }
        }
        return dao.getArtyList().map { list -> list.map { ArtyItem(it.id, it.title) } }
    }

    override suspend fun getArtyDetails(id: Long): ArtyItemDetails = service.getArtyDetails(id).data.toDomain()
}

private fun ArtyResponse.toEntity(): ArtyEntity = ArtyEntity(id, title)

private fun ArtyDetailedResponse.toDomain(): ArtyItemDetails = ArtyItemDetails(
    id, title, description, imageId
)

private fun List<ArtyResponse>.toDomain(): List<ArtyItem> = this.map {
    ArtyItem(
        it.id,
        it.title
    )
}
