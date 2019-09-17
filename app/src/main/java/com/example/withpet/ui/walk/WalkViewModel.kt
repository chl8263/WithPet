package com.example.withpet.ui.walk

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.withpet.core.BaseViewModel
import com.example.withpet.ui.hospital.hospitalMain.usecase.LocationUseCase
import com.example.withpet.ui.walk.usecase.WalkUseCase
import com.example.withpet.vo.LocationVO
import com.example.withpet.vo.walk.WalkBicycleDTO
import com.example.withpet.vo.walk.WalkParkDTO
import io.reactivex.android.schedulers.AndroidSchedulers

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
                .subscribe { t: List<WalkBicycleDTO>? -> _bicycleList.postValue(t) }
        )
    }

    fun getParkList() {
        addDisposable(
            walkUseCase.getParkList()
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { t: List<WalkParkDTO>? -> _parkList.postValue(t) }
        )
    }

    fun searchList(keyword : String){
        searchBicycleList(keyword)
        searchParkList(keyword)
    }

    private fun searchBicycleList(keyword: String) {
        addDisposable(
            walkUseCase.searchBicycleList(keyword)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { t: MutableList<WalkBicycleDTO>? -> _searchBicycleList.postValue(t) }
        )
    }

    private fun searchParkList(keyword: String) {
        addDisposable(
            walkUseCase.searchParkList(keyword)
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
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
            walkUseCase.insertBicycleList()
                .subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { }
            walkUseCase.insertParkList()
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