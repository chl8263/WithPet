package com.example.withpet.ui.hospital

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.withpet.core.BaseViewModel
import com.example.withpet.ui.hospital.usecase.LocationUseCase
import com.example.withpet.ui.hospital.usecase.impl.LocationUseCaseImpl
import com.example.withpet.util.Log
import com.example.withpet.vo.LocationVo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HospitalViewModel(private val locationUseCase : LocationUseCase) :BaseViewModel() {

    private val _currentLocation = MutableLiveData<LocationVo>()
    val currentLocation: LiveData<LocationVo>
        get() = _currentLocation

    fun getcurrentLocation(){
        addDisposable(
            locationUseCase.getCurrentLocation()
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {  t: LocationVo? ->
                    _currentLocation.postValue(t)
                }
        )
    }

}