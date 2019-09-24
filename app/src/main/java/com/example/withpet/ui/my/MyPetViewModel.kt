package com.example.withpet.ui.my

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.withpet.core.BaseViewModel
import com.example.withpet.util.LiveEvent
import com.example.withpet.util.Log
import com.example.withpet.vo.pet.PetDTO

class MyPetViewModel : BaseViewModel() {


    lateinit var petDTO: PetDTO

    val imageUrl = ObservableField<String>()
    val name = ObservableField<String>()
    val birthDay = ObservableField<String>()
    val gender = ObservableInt()
    val petNum = ObservableField<String>()
    val hospital = ObservableField<String>()

    val isDiaryListEmpty = ObservableBoolean(false)


    private val _goHospital = LiveEvent<Any>()
    val goHospital: LiveData<Any>
        get() = _goHospital

    private val _goPetNumInfo = MutableLiveData<String>()
    val goPetNumInfo: LiveData<String>
        get() = _goPetNumInfo

    private val _goPetNumUpdate = LiveEvent<Any>()
    val goPetNumUpdate: LiveData<Any>
        get() = _goPetNumUpdate

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


    fun clickHospital() {
        if (petDTO.hospital == null) _goHospital.call()
    }

    fun clickPetNum() {
        petDTO.petNum?.let {
            _goPetNumInfo.postValue(it)
        } ?: _goPetNumUpdate.call()
    }
}