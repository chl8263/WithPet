package com.example.withpet.ui.abandon.usecase

import com.example.withpet.vo.abandon.AbandonAnimalDTO
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface AbdDataSource {
    @GET("sido")
    fun getSido(
            @Query(value = "serviceKey", encoded = true) serviceKey: String
    ): Call<String>


    @Headers("Accept: application/json")
    @GET("abandonmentPublic")
    fun getList(
            @Query(value = "serviceKey", encoded = true) serviceKey: String,
            @Query(value = "bgnde") bgnde: String,
            @Query(value = "endde") endde: String,
            @Query(value = "upkind") upkind: Int,
            @Query(value = "upr_cd") upr_cd: Int,
            @Query(value = "org_cd") org_cd: Int?,
            @Query(value = "state") state: String?,
            @Query(value = "pageNo") pageNo: Int,
            @Query(value = "numOfRows") numOfRows: Int
    ): Single<AbandonAnimalDTO>

}