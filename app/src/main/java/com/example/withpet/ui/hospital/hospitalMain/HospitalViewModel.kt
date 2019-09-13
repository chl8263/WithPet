package com.example.withpet.ui.hospital.hospitalMain

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.withpet.core.BaseViewModel
import com.example.withpet.ui.hospital.hospitalMain.usecase.HistoryRepository
import com.example.withpet.ui.hospital.hospitalMain.usecase.HospitalRepository
import com.example.withpet.ui.hospital.hospitalMain.usecase.LocationUseCase
import com.example.withpet.vo.hospital.HospitalSearchDTO
import com.example.withpet.vo.LocationVO
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HospitalViewModel(private val locationUseCase : LocationUseCase ,
                        private val hospitalRepository: HospitalRepository,
                        private val historyRepository: HistoryRepository ) :BaseViewModel() {

    //----------------- LiveData ------------------

    private val _currentLocation = MutableLiveData<LocationVO>()
    val currentLocation: LiveData<LocationVO>
        get() = _currentLocation

    private val _hospitalList = MutableLiveData<ArrayList<HospitalSearchDTO>>()
    val hospitalList: LiveData<ArrayList<HospitalSearchDTO>>
        get() = _hospitalList

    private val _historyList = MutableLiveData<ArrayList<HospitalSearchDTO>>()
    val historyList: LiveData<ArrayList<HospitalSearchDTO>>
        get() = _historyList

    //----------------------------------------------

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
                    _hospitalList.postValue(t)
                }
        )
    }

    fun getHistoryData(){
        addDisposable(
            historyRepository.getHistoryData()
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {  t: ArrayList<HospitalSearchDTO>? ->
                    _historyList.postValue(t)
                }
        )
    }

}