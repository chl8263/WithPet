package com.example.withpet.net

import com.example.withpet.util.Log
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

/**
 * <uses-permission android:name="android.permission.INTERNET" />
 * <p>
 */
class Net(
    val BASE_URL: String,
    private val interceptors: Array<Interceptor>? = null, // 추가 interceptor
    private val isGsonConverter: Boolean = true,          // Gson Converter 사용여부
    private val isScalarsConverter: Boolean = true,       // Scalars Converter 사용여부 ( Respone String으로 받을 경우 )
    private val isRxJavaAdapter: Boolean = true,          // RxJavaAdapter 사용 여부
    private val connectTimeout: Long = 60,                // ConnectTimeout Default 30초
    private val writeTimeout: Long = 60,                  // WriteTimeout Default 30초
    private val readTimeout: Long = 60                    // ReadTimeout Default 30초
) {

    val retrofit: Retrofit

    init {

        val httpClient = getClientBuilder().apply {

            // Interceptor 추가
            interceptors?.let {
                it.forEach { interceptor ->
                    Log.i("interceptor 추가")
                    addInterceptor(interceptor)
                }
            }

            // Timeout Default 60 seconds
            connectTimeout(connectTimeout, TimeUnit.SECONDS)
            writeTimeout(writeTimeout, TimeUnit.SECONDS)
            readTimeout(readTimeout, TimeUnit.SECONDS)

        }.build()

        retrofit = Retrofit.Builder().apply {
            baseUrl(BASE_URL) // BaseUrl 설정
            if (isScalarsConverter) addConverterFactory(ScalarsConverterFactory.create())   // Respone String
            if (isGsonConverter) addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))         // Respone Gson
            if (isRxJavaAdapter) addCallAdapterFactory(RxJava2CallAdapterFactory.create())  // RxJava Adapter
            client(httpClient)
        }.build()
    }

    private fun getClientBuilder(): OkHttpClient.Builder {
        return OkHttpClient().newBuilder()
    }


    fun getUrl(url: String): String {
        if (url.startsWith("http")) return url
        var replace_url = url
        if (url.startsWith("/")) replace_url = url.replaceFirst("/", "")
        Log.i("replace url :$replace_url")

        return Companion.host + replace_url
    }

    fun getRetrofit(baseUrl: String): Retrofit =
        Retrofit.Builder().apply {
            baseUrl(baseUrl) // BaseUrl 설정
            addConverterFactory(GsonConverterFactory.create())
            addConverterFactory(ScalarsConverterFactory.create())
            addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            client(getClientBuilder().build())
        }.build()

    companion object {
        const val host = "https://maps.googleapis.com/maps/api/directions/"
    }
}