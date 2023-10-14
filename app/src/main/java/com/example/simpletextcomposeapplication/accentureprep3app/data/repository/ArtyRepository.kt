package com.example.simpletextcomposeapplication.accentureprep3app.data.repository

import com.example.simpletextcomposeapplication.accentureprep3app.data.remote.ArtyApi
import com.example.simpletextcomposeapplication.accentureprep3app.data.remote.ArtyDetailedResponse
import com.example.simpletextcomposeapplication.accentureprep3app.data.remote.ArtyResponse
import com.example.simpletextcomposeapplication.accentureprep3app.domain.ArtyItem
import com.example.simpletextcomposeapplication.accentureprep3app.domain.ArtyItemDetails
import javax.inject.Inject

interface ArtyRepository {
    suspend fun getArtyList(): List<ArtyItem>
    suspend fun getArtyDetails(id: Long): ArtyItemDetails
}

class ArtyRepositoryImpl @Inject constructor(
    private val service: ArtyApi
) : ArtyRepository {
    override suspend fun getArtyList(): List<ArtyItem> = service.getArtyList().data.toDomain()
    override suspend fun getArtyDetails(id: Long): ArtyItemDetails = service.getArtyDetails(id).data.toDomain()

}

private fun ArtyDetailedResponse.toDomain(): ArtyItemDetails = ArtyItemDetails(
    id, title, description, imageId
)

private fun List<ArtyResponse>.toDomain(): List<ArtyItem> = this.map {
    ArtyItem(
        it.id,
        it.title
    )
}
