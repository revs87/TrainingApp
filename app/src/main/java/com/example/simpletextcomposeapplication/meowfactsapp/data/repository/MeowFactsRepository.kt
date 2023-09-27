package com.example.simpletextcomposeapplication.meowfactsapp.data.repository

import android.content.Context
import com.example.simpletextcomposeapplication.meowfactsapp.data.remote.MeowFactsService
import com.example.simpletextcomposeapplication.meowfactsapp.data.repository.db.MeowFact
import com.example.simpletextcomposeapplication.meowfactsapp.data.repository.db.MeowFactsDao
import com.example.simpletextcomposeapplication.meowfactsapp.data.repository.db.createDb


class MeowFactsRepository(
    private val context: Context,
    private val service: MeowFactsService = MeowFactsService(),
    private val dao: MeowFactsDao = createDb(context).meowFactsDao
) {
    suspend fun getAllMeowFacts() = dao.getAllMeowFacts().map { it.message }

    suspend fun getMoreMeowFacts(count: Int): List<String> {
        val response = service.getMeowFacts(count)
        response.list.forEach {
            dao.insertMeowFact(MeowFact(it))
        }

        return getAllMeowFacts()
    }

}