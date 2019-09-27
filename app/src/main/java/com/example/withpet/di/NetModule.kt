package com.example.withpet.di

import com.example.withpet.net.Net
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit

@JvmField
val netModule = module {
    single { Net(BASE_URL = "http://openapi.animal.go.kr/openapi/service/rest/abandonmentPublicSrvc/", interceptors = arrayOf(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })) }
}

inline fun <reified T> createNetService(net: Net): T {
    return net.retrofit.create(T::class.java)
}

inline fun <reified T> createWebService(retrofit: Retrofit): T {
    return retrofit.create(T::class.java)
}