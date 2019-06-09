package com.example.withpet.ui.hospital

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.withpet.core.BaseViewModel
import com.example.withpet.ui.hospital.usecase.LocationUseCase
import com.example.withpet.vo.LocationVO
import io.reactivex.android.schedulers.AndroidSchedulers

class HospitalViewModel(private val locationUseCase : LocationUseCase) :BaseViewModel() {

    private val _currentLocation = MutableLiveData<LocationVO>()
    val currentLocation: LiveData<LocationVO>
        get() = _currentLocation

    fun getcurrentLocation(){
        addDisposable(
            locationUseCase.getCurrentLocation()
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {  t: LocationVO? ->
                    _currentLocation.postValue(t)
                }
        )
    }

}