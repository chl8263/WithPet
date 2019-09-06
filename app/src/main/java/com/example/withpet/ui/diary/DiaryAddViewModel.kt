package com.example.withpet.ui.diary

import androidx.lifecycle.LiveData
import com.example.withpet.core.BaseViewModel
import com.example.withpet.ui.diary.usecase.DiaryAddUseCase
import com.example.withpet.util.LiveEvent

class DiaryAddViewModel(private val diaryAddUseCase: DiaryAddUseCase) : BaseViewModel() {


    private val _callGallery = LiveEvent<Any>()
    val callGallery: LiveData<Any>
        get() = _callGallery


    fun add() {

    }

    fun gallery() = _callGallery.call()
}