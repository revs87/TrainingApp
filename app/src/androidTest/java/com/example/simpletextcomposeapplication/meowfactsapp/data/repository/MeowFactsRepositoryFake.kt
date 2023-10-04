package com.example.simpletextcomposeapplication.meowfactsapp.data.repository

class MeowFactsRepositoryFake : MeowFactsRepository {

    private val list = mutableListOf<String>()

    override suspend fun getAllMeowFacts(): List<String> {
        return list
    }

    override suspend fun getMoreMeowFacts(count: Int): List<String> {
        repeat(count) {
            list.add(DefaultMessage)
        }
        return list
    }

    companion object {
        const val DefaultMessage = "Testing"
    }
}