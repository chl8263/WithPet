package com.example.withpet.ui.walk

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.withpet.core.BaseViewModel
import com.example.withpet.ui.hospital.hospitalMain.usecase.LocationUseCase
import com.example.withpet.ui.walk.usecase.WalkMainUseCase
import com.example.withpet.util.progress
import com.example.withpet.util.with
import com.example.withpet.vo.LocationVO
import com.example.withpet.vo.walk.WalkBicycleDTO
import com.example.withpet.vo.walk.WalkParkDTO
import io.reactivex.Observer
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

class WalkMainViewModel(
        private val locationUseCase: LocationUseCase
        , private val walkMainUseCase: WalkMainUseCase
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
        locationUseCase.getCurrentLocation()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<LocationVO> {
                    override fun onComplete() {
                    }

                    override fun onSubscribe(d: Disposable) {
                        _showProgress.postValue(true)
                    }

                    override fun onNext(t: LocationVO) {
                        _showProgress.postValue(false)
                        _currentLocation.postValue(t)
                    }

                    override fun onError(e: Throwable) {
                        _showProgress.postValue(false)
                    }
                })
    }

    fun getBicycleList() {
        addDisposable(
                walkMainUseCase.getBicycleList()
                        .with()
                        .progress(_showProgress)
                        .subscribe { t: List<WalkBicycleDTO>? -> _bicycleList.postValue(t) }
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

    fun searchList(keyword: String) {
        if (keyword.trim().isNotEmpty()) {
            searchBicycleList(keyword)
            searchParkList(keyword)
        }
    }

    private fun searchBicycleList(keyword: String) {
        addDisposable(
                walkMainUseCase.searchBicycleList(keyword)
                        .with()
                        .progress(_showProgress)
                        .subscribe { t: MutableList<WalkBicycleDTO>? -> _searchBicycleList.postValue(t) }
        )
    }

    private fun searchParkList(keyword: String) {
        addDisposable(
                walkMainUseCase.searchParkList(keyword)
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
            walkMainUseCase.insertBicycleList().with()
            walkMainUseCase.insertParkList().with()
        }
//        _showAdminMenu.postValue(true)
    }

    fun dismiss() {
        _dismiss.postValue(true)
    }

}