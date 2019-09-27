package com.example.withpet.ui.abandon

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.withpet.core.BaseViewModel
import com.example.withpet.ui.abandon.usecase.AbdUseCase
import com.example.withpet.util.Log
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AbdViewModel(private val useCase: AbdUseCase) : BaseViewModel() {

    // data
    private val _getSido = MutableLiveData<String>()
    val getSido: LiveData<String>
        get() = _getSido

    fun getSido() {
        useCase.getSido().enqueue(object : Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.w("getSido Fail : ${t.message}")
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                response.body()?.let {
                    Log.w("getSido Success \n$it")
                } ?: run {
                    response.errorBody()?.let { Log.w("getSido Fail : ${JSONObject(it.string()).getString("error_message")}") }
                }
            }
        })
    }
}