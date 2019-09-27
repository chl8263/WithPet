package com.example.withpet.ui.my

import android.content.Intent
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.withpet.core.BaseViewModel
import com.example.withpet.ui.diary.usecase.DiaryUseCase
import com.example.withpet.ui.pet.PetEditActivity
import com.example.withpet.ui.pet.usecase.PetUseCase
import com.example.withpet.util.LiveEvent
import com.example.withpet.util.Log
import com.example.withpet.util.progress
import com.example.withpet.util.with
import com.example.withpet.vo.diary.DiaryDTO
import com.example.withpet.vo.pet.PetDTO

class MyPetViewModel(private val petUseCase: PetUseCase,
                     private val diaryUseCase: DiaryUseCase) : BaseViewModel() {


    lateinit var petDTO: PetDTO

    val imageUrl = ObservableField<String>()                // imageUrl
    val name = ObservableField<String>()                    // 이름
    val birthDay = ObservableField<String>()                // 생일
    val gender = ObservableInt()                            // 성별
    val petNum = ObservableField<String>()                  // 동물등록번호
    val hospital = ObservableField<String>()                // 병원
    val diaryList = ObservableArrayList<DiaryDTO>()         // diaryList

    val isDiaryListEmpty = ObservableBoolean(true)

    private val _goPetNumInfo = MutableLiveData<String>()
    val goPetNumInfo: LiveData<String>
        get() = _goPetNumInfo

    private val _showOption = LiveEvent<Any>()
    val showOption: LiveData<Any>
        get() = _showOption

    private val _showProgress = MutableLiveData<Boolean>()   // Error Message
    val showProgress: LiveData<Boolean>
        get() = _showProgress

    private val _petDeleteReload = LiveEvent<Any>()
    val petDeleteReload: LiveData<Any>
        get() = _petDeleteReload

    private val _addDiary = MutableLiveData<String>()
    val addDiary: LiveData<String>
        get() = _addDiary

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    private val _goPetEdit = LiveEvent<Any>()
    val goPetEdit: LiveData<Any>
        get() = _goPetEdit

    fun initData(petDTO: PetDTO) {
        Log.i(petDTO)

        this.petDTO = petDTO

        imageUrl.set(petDTO.imageUrl)
        name.set(petDTO.name)
        birthDay.set(petDTO.birthDay)
        gender.set(petDTO.gender)
        petNum.set(petDTO.petNum)

        val hospitalName = petDTO.hospital?.name ?: "병원을 등록 해 보세요."
        hospital.set(hospitalName)

        val petNum = petDTO.petNum ?: "동물등록번호를 등록 해 보세요"
        this.petNum.set(petNum)
    }

    fun clickUpdate() = _goPetEdit.call()

    fun clickHospital() {
        if (petDTO.hospital?.name != null) {
            _goPetEdit.call()
        }
    }

    fun clickPetNum() {
        petDTO.petNum?.let {
            _goPetNumInfo.postValue(it)
        } ?: _goPetEdit.call()
    }

    fun clickOption() = _showOption.call()
    fun clickAddDiary() = _addDiary.postValue(petDTO.name)

    fun petEdit(intent: Intent) {
        intent.getSerializableExtra(PetEditActivity.RES.PET_DTO)?.let { serializable ->
            val petData = serializable as PetDTO
            initData(petData)
        }
    }

    fun petDelete() {
        launch {
            petUseCase.delete(petDTO)
                    .with()
                    .progress(_showProgress)
                    .subscribe({
                        if (it) _petDeleteReload.call()
                    }, { _errorMessage.postValue(it.message) })
        }
    }

    fun getDiaryList() {
        launch {
            diaryUseCase.getDiaryList()
                    .with()
                    .progress(_showProgress)
                    .subscribe({
                        isDiaryListEmpty.set(it.isEmpty())

                        if (it.isNotEmpty()) {
                            diaryList.clear()
                            diaryList.addAll(it)
                        }
                    }, { _errorMessage.postValue(it.message) })
        }
    }
}