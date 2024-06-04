package com.example.simpletextcomposeapplication.itunestop100.data.remote

import retrofit2.http.GET

interface ITunesAlbumsApi {

    @GET("limit=100/json")
    suspend fun getAlbums(): ITunesAlbumsResponse

}