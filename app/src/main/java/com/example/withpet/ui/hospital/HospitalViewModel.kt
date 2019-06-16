package com.example.withpet.ui.hospital

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.withpet.core.BaseViewModel
import com.example.withpet.ui.hospital.usecase.HospitalRepository
import com.example.withpet.ui.hospital.usecase.LocationUseCase
import com.example.withpet.util.Log
import com.example.withpet.vo.HospitalSearchDTO
import com.example.withpet.vo.LocationVO
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HospitalViewModel(private val locationUseCase : LocationUseCase , private val hospitalRepository: HospitalRepository) :BaseViewModel() {

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

    fun getHospitalSearchData(searchValue : String){
        addDisposable(
            hospitalRepository.getHospitalSearchData(searchValue = searchValue)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {t: ArrayList<HospitalSearchDTO>? ->

                }

        )
    }

}