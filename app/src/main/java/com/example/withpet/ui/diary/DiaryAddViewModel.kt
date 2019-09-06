package com.example.withpet.ui.diary

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.withpet.core.BaseViewModel
import com.example.withpet.ui.diary.usecase.DiaryAddUseCase
import com.example.withpet.util.LiveEvent
import com.example.withpet.util.Log
import com.example.withpet.util.SDF
import com.example.withpet.util.Storage
import kotlin.math.log10

class DiaryAddViewModel(private val diaryAddUseCase: DiaryAddUseCase) : BaseViewModel() {


    val title = ObservableField<String>()           // 제목
    val content = ObservableField<String>()         // 내용
    val date = ObservableField<String>()            // 날짜

    private val _callGallery = LiveEvent<Any>()     // Gallery 호출
    val callGallery: LiveData<Any>
        get() = _callGallery

    private val _showCalendar = LiveEvent<Any>()    // Calendar 호출
    val showCalendar: LiveData<Any>
        get() = _showCalendar

    private val _alertMessage = MutableLiveData<String>()   // Calendar 호출
    val alertMessage: LiveData<String>
        get() = _alertMessage


    fun add() {

    }

    fun gallery() = _callGallery.call()

    fun calendar() = _showCalendar.call()

    fun resultCalendar(year: Int, month: Int, dayOfMonth: Int) {
        Log.i("year : $year, month : $month, dayOfMonth:$dayOfMonth")
        val plusMonth = month + 1

        val dayLength = (log10(dayOfMonth.toDouble()) + 1).toInt()
        val monthLength = (log10(plusMonth.toDouble()) + 1).toInt()

        val formatDay = if (dayLength == 1) "0$dayOfMonth" else dayOfMonth.toString()
        val formatMonth = if (monthLength == 1) "0$plusMonth" else plusMonth.toString()

        val selectDate = "$year$formatMonth$formatDay"
        val parseSelectDate = SDF.yyyymmdd.parse(selectDate)

        date.set(SDF.yyyymmdd_2.format(parseSelectDate))
    }

    fun validation() {
        val getTitle = title.get()
        val getContent = content.get()
        val getDate = date.get()

        if (getTitle.isNullOrEmpty()) {
            _alertMessage.postValue("제목을 입력 해 주세요.")
            return
        }

        if (getContent.isNullOrEmpty()) {
            _alertMessage.postValue("내용을 입력 해 주세요.")
            return
        }

        if (getDate.isNullOrEmpty()) {
            _alertMessage.postValue("날짜를 입력 해 주세요.")
            return
        }

        add()
    }
}