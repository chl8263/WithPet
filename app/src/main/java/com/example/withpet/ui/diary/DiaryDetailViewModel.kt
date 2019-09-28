package com.example.withpet.ui.diary

import androidx.databinding.ObservableField
import com.example.withpet.core.BaseViewModel
import com.example.withpet.ui.diary.usecase.DiaryUseCase

class DiaryDetailViewModel(val diaryUseCase: DiaryUseCase) : BaseViewModel() {

    val content = ObservableField<String>()
    val imageUrl = ObservableField<String>()
    val date = ObservableField<String>()


    fun init() {

    }

}