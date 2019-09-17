package com.example.withpet.ui.walk.usecase

import retrofit2.Call
import retrofit2.http.*


interface WalkDataSource {

    /** todo
     * api 통신 어떻게..?
     * 우선 임시로 json 파일 받아서 처리
     */

    @GET("json")
    fun getDirection(
            @Query("origin") origin: String,
            @Query("destination") destination: String,
            @Query("mode") mode: String,
            @Query("key") key: String
    ): Call<String>

}