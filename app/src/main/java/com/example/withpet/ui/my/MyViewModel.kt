package com.example.withpet.ui.my

import androidx.databinding.ObservableArrayList
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.withpet.core.BaseViewModel
import com.example.withpet.ui.pet.usecase.PetUseCase
import com.example.withpet.util.Log
import com.example.withpet.util.progress
import com.example.withpet.util.with
import com.example.withpet.vo.pet.PetDTO

class MyViewModel(private val petUseCase: PetUseCase) : BaseViewModel() {

    val petList = ObservableArrayList<PetDTO>()

    private val _fragmentList = MutableLiveData<MutableList<Fragment>>()   // Error Message
    val fragmentList: LiveData<MutableList<Fragment>>
        get() = _fragmentList

    private val _showProgress = MutableLiveData<Boolean>()   // Error Message
    val showProgress: LiveData<Boolean>
        get() = _showProgress


    fun getPatList() {
        launch {
            petUseCase.getPetList()
                    .with()
                    .progress(_showProgress)
                    .subscribe({
                        it?.let { list -> resultPatList(list) }
                    }, { exception ->
                        Log.e("join Error : ${exception.message}")
                        exception.printStackTrace()
                    })
        }
    }

    private fun resultPatList(list: List<PetDTO>) {
        Log.i(list)
        petList.clear()
        petList.addAll(list)

        val dummyFragmentList = mutableListOf<Fragment>()
        dummyFragmentList.clear()
        for (i in 0 until petList.size) {
            dummyFragmentList.add(MyPetFragment.newInstance(i))
        }

        _fragmentList.postValue(dummyFragmentList)
    }
}