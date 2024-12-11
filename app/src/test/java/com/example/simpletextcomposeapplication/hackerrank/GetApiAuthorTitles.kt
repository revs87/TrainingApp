package com.example.simpletextcomposeapplication.hackerrank

import com.google.gson.Gson
import org.junit.jupiter.api.Test
import java.net.HttpURLConnection
import java.net.URL


class GetApiAuthorTitles {

    fun AuthorTitle.getTitle(): String = this.title ?: this.story_title ?: "toIgnore"

    /*
     * Helper function that converts an object of type T -> JSON string.
     */
    private inline fun <T> toJson(model: T): String = Gson().toJson(model)

    /*
    * Helper function that converts a JSON string -> to an object of type T.
    */
    private inline fun <reified T> fromJson(json: String): T = Gson().fromJson(json, T::class.java)

    data class AuthorTitle(
        val title: String?,
        val url: String,
        val author: String,
        val num_comments: Long,
        val story_id: Long?,
        val story_title: String? = null,
        val story_url: String?,
        val parent_id: Long?,
        val created_at: Long,
    )

    data class Page(
        val page: Int,
        val per_page: Int,
        val total: Int,
        val total_pages: Int,
        val data: List<AuthorTitle>
    )

    @Test
    fun test() {
        val author: String = "epaga"


        val titles: MutableList<String> = mutableListOf()
        var currentPage = 1
        var totalPages = 1
        val queryId =
            "https://jsonmock.hackerrank.com/api/articles?author=$author&page=$currentPage"

        val url = URL(queryId)
        val urlConnection = url.openConnection() as HttpURLConnection
        urlConnection.requestMethod = "GET"

        try {

            while (currentPage <= totalPages) {
                if (urlConnection.responseCode == HttpURLConnection.HTTP_OK) {
                    urlConnection.inputStream.bufferedReader().use { reader ->

                        val response: Page = fromJson(reader.readText())

                        currentPage = response.page
                        totalPages = response.total_pages

                        response.data.forEach {
                            val title = it.getTitle()
                            if (title != "toIgnore") {
                                titles.add(title)
                            }
                        }

                    }
                }
                currentPage++
            }
        } catch (e: Exception) {
            print(e.printStackTrace())
        } finally {
            urlConnection.disconnect()
        }

        println(titles)
    }
}
