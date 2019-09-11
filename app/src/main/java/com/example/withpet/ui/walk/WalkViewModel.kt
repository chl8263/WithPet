package com.example.withpet.ui.walk

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.withpet.core.BaseViewModel
import com.example.withpet.ui.hospital.usecase.LocationUseCase
import com.example.withpet.ui.walk.usecase.WalkUseCase
import com.example.withpet.util.Log
import com.example.withpet.vo.LocationVO
import com.example.withpet.vo.walk.WalkBicycleDTO
import com.example.withpet.vo.walk.WalkBicycleDTOList
import io.reactivex.android.schedulers.AndroidSchedulers

class WalkViewModel(
        private val locationUseCase: LocationUseCase
        , private val walkUseCase: WalkUseCase
) : BaseViewModel() {

    // data
    private val _currentLocation = MutableLiveData<LocationVO>()
    val currentLocation: LiveData<LocationVO>
        get() = _currentLocation

    private val _bicycleList = MutableLiveData<ArrayList<WalkBicycleDTO>>()
    val bicycleList: LiveData<ArrayList<WalkBicycleDTO>>
        get() = _bicycleList

    fun getCurrentLocation() {
        addDisposable(
                locationUseCase.getCurrentLocation()
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { t: LocationVO? -> _currentLocation.postValue(t) }
        )
    }

    fun getBicycleList() {
        addDisposable(
                walkUseCase.getBicycleList()
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { t: WalkBicycleDTOList? -> _bicycleList.postValue(t?.bicycleDTOList) }
        )
    }

    // view
    private val _showAdminMenu = MutableLiveData<Boolean>()
    val showAdminMenu : LiveData<Boolean>
        get() = _showAdminMenu

    private val _dismiss = MutableLiveData<Boolean>()
    val dismiss: LiveData<Boolean>
        get() = _dismiss

    fun showAdminMenu(){
        run{
            walkUseCase.insertBicycleList()
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { }
        }
//        _showAdminMenu.postValue(true)
    }

    fun dismiss() {
        _dismiss.postValue(true)
    }

}