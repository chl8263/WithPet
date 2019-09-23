package com.example.withpet.ui.walk

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.withpet.core.BaseViewModel
import com.example.withpet.ui.walk.usecase.WalkDirectionUseCase
import com.google.android.gms.maps.model.LatLng

class WalkDirectionViewModel(private val walkDirectionUseCase: WalkDirectionUseCase) : BaseViewModel() {

    // data
    private val _getDirection = MutableLiveData<String>()
    val getDirection: LiveData<String>
        get() = _getDirection

    fun getDirection(destinationName: String?, destination: LatLng) {
        walkDirectionUseCase.getDirection(destinationName, destination)
    }
}