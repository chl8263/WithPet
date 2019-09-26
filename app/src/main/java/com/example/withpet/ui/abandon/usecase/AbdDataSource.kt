package com.example.withpet.ui.abandon.usecase

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AbdDataSource {
    @GET("sido")
    fun getSido(
            @Query(value = "serviceKey", encoded = true) serviceKey : String
    ): Call<String>

}