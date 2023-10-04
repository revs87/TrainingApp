package com.example.simpletextcomposeapplication.meowfactsapp.data.repository

import com.example.simpletextcomposeapplication.meowfactsapp.data.remote.MeowFactsService
import com.example.simpletextcomposeapplication.meowfactsapp.data.repository.db.MeowFact
import com.example.simpletextcomposeapplication.meowfactsapp.data.repository.db.MeowFactsDao
import javax.inject.Inject


interface MeowFactsRepository {
    suspend fun getAllMeowFacts(): List<String>

    suspend fun getMoreMeowFacts(count: Int): List<String>
}

class MeowFactsRepositoryImpl @Inject constructor(
    private val service: MeowFactsService,
    private val dao: MeowFactsDao
) : MeowFactsRepository {
    override suspend fun getAllMeowFacts() = dao.getAllMeowFacts().map { it.message }

    override suspend fun getMoreMeowFacts(count: Int): List<String> {
        val response = service.getMeowFacts(count)
        response.list.forEach {
            dao.insertMeowFact(MeowFact(it))
        }

        return getAllMeowFacts()
    }

}