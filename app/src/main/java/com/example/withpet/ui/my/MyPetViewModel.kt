package com.example.withpet.ui.my

import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import com.example.withpet.core.BaseViewModel
import com.example.withpet.util.Log
import com.example.withpet.vo.pet.PetDTO

class MyPetViewModel : BaseViewModel() {

    val imageUrl = ObservableField<String>()
    val name = ObservableField<String>()
    val birthDay = ObservableField<String>()
    val gender = ObservableInt()
    val petNum = ObservableField<String>()

    fun initData(petDTO: PetDTO) {
        Log.i(petDTO)
        imageUrl.set(petDTO.imageUrl)
        name.set(petDTO.name)
        birthDay.set(petDTO.birthDay)
        gender.set(petDTO.gender)
        petNum.set(petDTO.petNum)
    }

}