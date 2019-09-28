package com.example.withpet.ui.abandon

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.withpet.core.BaseViewModel
import com.example.withpet.ui.abandon.enums.SigunguEnum
import com.example.withpet.ui.abandon.usecase.AbdUseCase
import com.example.withpet.util.Log
import com.example.withpet.util.progress
import com.example.withpet.util.with
import com.example.withpet.vo.abandon.AbandonAnimalDTO
import kotlin.math.ceil

class AbdViewModel(private val useCase: AbdUseCase) : BaseViewModel() {

    private var maxPageNo: Int = 1
    private var currentPageNo: Int = 0
    private var currentSigungu: SigunguEnum = SigunguEnum.전체

    // data
    val currentSigunguName = ObservableField<String>(currentSigungu.displayName)

    private val _abandonAnimalResult = MutableLiveData<ArrayList<AbandonAnimalDTO>>()
    val abandonAnimalResult: LiveData<ArrayList<AbandonAnimalDTO>>
        get() = _abandonAnimalResult

    private val _clear = MutableLiveData<Boolean>()
    val clear: LiveData<Boolean>
        get() = _clear

    // show loading
    private val _showProgress = MutableLiveData<Boolean>()
    val showProgress: LiveData<Boolean>
        get() = _showProgress

    fun getAbandonAnimalList() {
        if (currentPageNo < maxPageNo) {
            addDisposable(
                useCase.getList(currentSigungu.code, ++currentPageNo)
                    .with()
                    .progress(_showProgress)
                    .subscribe({ success ->
                        _abandonAnimalResult.postValue(success.response.body.items.list)
                        maxPageNo = ceil(success.response.body.totalCount / 15.0).toInt()
                    }, { error -> Log.w("abandonAnimalList Fail : ${error.message}") })
            )
        }
    }

    fun select(enum: SigunguEnum) {
        currentPageNo = 0
        currentSigungu = enum
        maxPageNo = 1
        currentSigunguName.set(currentSigungu.displayName)
        getAbandonAnimalList()
        _clear.postValue(true)
    }
}