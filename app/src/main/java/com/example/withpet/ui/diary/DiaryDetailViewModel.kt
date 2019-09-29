package com.example.withpet.ui.diary

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.withpet.core.BaseViewModel
import com.example.withpet.ui.diary.usecase.DiaryUseCase
import com.example.withpet.util.LiveEvent
import com.example.withpet.util.progress
import com.example.withpet.util.with
import com.example.withpet.vo.diary.DiaryDTO

class DiaryDetailViewModel(private val diaryUseCase: DiaryUseCase) : BaseViewModel() {

    lateinit var diaryDTO: DiaryDTO
    lateinit var petName: String

    val content = ObservableField<String>()
    val imageUrl = ObservableField<String>()
    val date = ObservableField<String>()

    private val _showOption = LiveEvent<Any>()
    val showOption: LiveEvent<Any>
        get() = _showOption

    private val _showProgress = MutableLiveData<Boolean>()   // Progress
    val showProgress: LiveData<Boolean>
        get() = _showProgress

    private val _goEdit = MutableLiveData<DiaryDTO>()   // Progress
    val goEdit: LiveData<DiaryDTO>
        get() = _goEdit

    fun init(diaryDTO: DiaryDTO) {
        content.set(diaryDTO.content)
        imageUrl.set(diaryDTO.imageUrl)
        date.set(diaryDTO.date)
        this.diaryDTO = diaryDTO
    }

    fun delete() {
        launch {
            diaryUseCase.delete(diaryDTO, petName)
                    .with()
                    .progress(_showProgress)
                    .subscribe()
        }
    }

    fun goEdit() = _goEdit.postValue(diaryDTO)

}