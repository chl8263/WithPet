package com.example.withpet.ui.walk

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.withpet.core.BaseViewModel

class WalkViewModel : BaseViewModel() {

    private val _goMapActivity = MutableLiveData<Boolean>()
    val goMapActivity: LiveData<Boolean>
        get() = _goMapActivity

    fun goMapActivity() {
        _goMapActivity.postValue(true)
    }

}