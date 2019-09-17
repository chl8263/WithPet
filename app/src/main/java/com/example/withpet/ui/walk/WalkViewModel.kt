package com.example.withpet.ui.walk

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.withpet.core.BaseViewModel
import com.example.withpet.ui.hospital.hospitalMain.usecase.LocationUseCase
import com.example.withpet.ui.walk.usecase.WalkUseCase
import com.example.withpet.util.progress
import com.example.withpet.util.with
import com.example.withpet.vo.LocationVO
import com.example.withpet.vo.walk.WalkBicycleDTO
import com.example.withpet.vo.walk.WalkParkDTO
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class WalkViewModel(
        private val locationUseCase: LocationUseCase
        , private val walkUseCase: WalkUseCase
) : BaseViewModel() {

    // data
    private val _currentLocation = MutableLiveData<LocationVO>()
    val currentLocation: LiveData<LocationVO>
        get() = _currentLocation

    private val _bicycleList = MutableLiveData<List<WalkBicycleDTO>>()
    val bicycleList: LiveData<List<WalkBicycleDTO>>
        get() = _bicycleList

    private val _parkList = MutableLiveData<List<WalkParkDTO>>()
    val parkList: LiveData<List<WalkParkDTO>>
        get() = _parkList

    private val _searchBicycleList = MutableLiveData<MutableList<WalkBicycleDTO>>()
    val searchBicycleList: LiveData<MutableList<WalkBicycleDTO>>
        get() = _searchBicycleList

    private val _searchParkList = MutableLiveData<MutableList<WalkParkDTO>>()
    val searchParkList: LiveData<MutableList<WalkParkDTO>>
        get() = _searchParkList


    // view
    private val _showProgress = MutableLiveData<Boolean>()
    val showProgress: LiveData<Boolean>
        get() = _showProgress

    fun getCurrentLocation() {
        addDisposable(
                locationUseCase.getCurrentLocation()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .subscribe { t: LocationVO? -> _currentLocation.postValue(t) }
        )
    }

    fun getBicycleList() {
        addDisposable(
                walkUseCase.getBicycleList()
                        .with()
                        .progress(_showProgress)
                        .subscribe { t: List<WalkBicycleDTO>? -> _bicycleList.postValue(t) }
        )
    }

    fun getParkList() {
        addDisposable(
                walkUseCase.getParkList()
                        .with()
                        .progress(_showProgress)
                        .subscribe { t: List<WalkParkDTO>? -> _parkList.postValue(t) }
        )
    }

    fun searchList(keyword: String) {
        if (keyword.trim().isNotEmpty()) {
            searchBicycleList(keyword)
            searchParkList(keyword)
        }
    }

    private fun searchBicycleList(keyword: String) {
        addDisposable(
                walkUseCase.searchBicycleList(keyword)
                        .with()
                        .progress(_showProgress)
                        .subscribe { t: MutableList<WalkBicycleDTO>? -> _searchBicycleList.postValue(t) }
        )
    }

    private fun searchParkList(keyword: String) {
        addDisposable(
                walkUseCase.searchParkList(keyword)
                        .with()
                        .progress(_showProgress)
                        .subscribe { t: MutableList<WalkParkDTO>? -> _searchParkList.postValue(t) }
        )
    }

    // view
    private val _showAdminMenu = MutableLiveData<Boolean>()
    val showAdminMenu: LiveData<Boolean>
        get() = _showAdminMenu

    private val _dismiss = MutableLiveData<Boolean>()
    val dismiss: LiveData<Boolean>
        get() = _dismiss

    fun showAdminMenu() {
        run {
            walkUseCase.insertBicycleList().with()
            walkUseCase.insertParkList().with()
        }
//        _showAdminMenu.postValue(true)
    }

    fun dismiss() {
        _dismiss.postValue(true)
    }

}