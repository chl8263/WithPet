package com.example.withpet.ui.walk

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.withpet.core.BaseViewModel
import com.example.withpet.ui.hospital.hospitalMain.usecase.LocationUseCase
import com.example.withpet.ui.walk.usecase.WalkMainUseCase
import com.example.withpet.util.progress
import com.example.withpet.util.with
import com.example.withpet.vo.LocationVO
import com.example.withpet.vo.walk.WalkParkDTO
import com.example.withpet.vo.walk.WalkTrailDTO
import io.reactivex.android.schedulers.AndroidSchedulers

class WalkMainViewModel(
        private val locationUseCase: LocationUseCase
        , private val walkMainUseCase: WalkMainUseCase
) : BaseViewModel() {

    // data
    private val _currentLocation = MutableLiveData<LocationVO>()
    val curLocation: LiveData<LocationVO>
        get() = _currentLocation

    private val _parkList = MutableLiveData<List<WalkParkDTO>>()
    val parkList: LiveData<List<WalkParkDTO>>
        get() = _parkList

    private val _trailList = MutableLiveData<List<WalkTrailDTO>>()
    val trailList: LiveData<List<WalkTrailDTO>>
        get() = _trailList

    private val _searchParkList = MutableLiveData<MutableList<WalkParkDTO>>()
    val searchParkList: LiveData<MutableList<WalkParkDTO>>
        get() = _searchParkList

    private val _searchTrailList = MutableLiveData<MutableList<WalkTrailDTO>>()
    val searchTrailList: LiveData<MutableList<WalkTrailDTO>>
        get() = _searchTrailList

    // view
    private val _showProgress = MutableLiveData<Boolean>()
    val showProgress: LiveData<Boolean>
        get() = _showProgress

    fun getCurrentLocation() {
        addDisposable(
                locationUseCase.getCurrentLocation()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .subscribe { _currentLocation.postValue(it) }
        )
    }

    fun getParkList() {
        addDisposable(
                walkMainUseCase.getParkList()
                        .with()
                        .progress(_showProgress)
                        .subscribe { t: List<WalkParkDTO>? -> _parkList.postValue(t) }
        )
    }

    fun getTrailList() {
        addDisposable(
                walkMainUseCase.getTrailList()
                        .with()
                        .progress(_showProgress)
                        .subscribe { t: List<WalkTrailDTO>? -> _trailList.postValue(t) }
        )
    }

    fun searchList(keyword: String) {
        if (keyword.trim().isNotEmpty()) {
            searchParkList(keyword)
            searchTrailList(keyword)
        }
    }

    private fun searchParkList(keyword: String) {
        addDisposable(
                walkMainUseCase.searchParkList(keyword)
                        .with()
                        .progress(_showProgress)
                        .subscribe { t: MutableList<WalkParkDTO>? -> _searchParkList.postValue(t) }
        )
    }

    private fun searchTrailList(keyword: String) {
        addDisposable(
                walkMainUseCase.searchTrailList(keyword)
                        .with()
                        .progress(_showProgress)
                        .subscribe { t: MutableList<WalkTrailDTO>? -> _searchTrailList.postValue(t) }
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
//            walkMainUseCase.insertParkList()
            walkMainUseCase.insertTrailList()
        }
//        _showAdminMenu.postValue(true)
    }

    fun dismiss() {
        _dismiss.postValue(true)
    }

}