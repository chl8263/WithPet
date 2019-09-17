package com.example.withpet.ui.my

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.withpet.core.BaseViewModel
import com.example.withpet.ui.pat.usecase.PatUseCase
import com.example.withpet.util.Log
import com.example.withpet.vo.pat.PatDTO
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MyViewModel(private val patUseCase: PatUseCase) : BaseViewModel() {

    val patList = ObservableArrayList<PatDTO>()

    private val _showProgress = MutableLiveData<Boolean>()   // Error Message
    val showProgress: LiveData<Boolean>
        get() = _showProgress


    fun getPatList() {
        launch {
            patUseCase.getPatList()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .doOnSubscribe { _showProgress.postValue(true) }
                    .doOnSuccess { _showProgress.postValue(false) }
                    .doOnError { _showProgress.postValue(false) }
                    .subscribe({
                        Log.i(it)
                        it?.let { list ->
                            Log.i(list)
                            patList.clear()
                            patList.addAll(list)
                        }
                    }, { exception ->
                        Log.e("join Error : ${exception.message}")
                        exception.printStackTrace()
                    })
        }
    }
}