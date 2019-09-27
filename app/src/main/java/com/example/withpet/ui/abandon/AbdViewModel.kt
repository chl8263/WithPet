package com.example.withpet.ui.abandon

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.withpet.core.BaseViewModel
import com.example.withpet.ui.abandon.enums.eSigungu
import com.example.withpet.ui.abandon.usecase.AbdUseCase
import com.example.withpet.util.Log
import com.example.withpet.util.progress
import com.example.withpet.util.with
import com.example.withpet.vo.abandon.AbandonAnimalDTO
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AbdViewModel(private val useCase: AbdUseCase) : BaseViewModel() {

    // data
    private val _getSido = MutableLiveData<String>()
    val getSido: LiveData<String>
        get() = _getSido

    private val _abandonAnimalList = MutableLiveData<ArrayList<AbandonAnimalDTO>>()
    val abandonAnimalList: LiveData<ArrayList<AbandonAnimalDTO>>
        get() = _abandonAnimalList

    // show loading
    private val _showProgress = MutableLiveData<Boolean>()
    val showProgress: LiveData<Boolean>
        get() = _showProgress

    fun getSido() {
        useCase.getSido().enqueue(object : Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.w("getSido Fail : ${t.message}")
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                response.body()?.let {
                    Log.w("getSido Success \n$it")
                } ?: run {
                    response.errorBody()
                            ?.let { Log.w("getSido Fail : ${JSONObject(it.string()).getString("error_message")}") }
                }
            }
        })
    }

    fun getList(
            eSigungu: eSigungu,
            pageNo: Int
    ) {
        addDisposable(
                useCase.getList(eSigungu, pageNo)
                        .with()
                        .progress(_showProgress)
                        .subscribe({ success -> _abandonAnimalList.postValue(success.response.body.items.list) }
                                , { error -> Log.w("abandonAnimalList Fail : ${error.message}") })
        )
    }
}