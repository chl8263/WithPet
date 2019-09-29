package com.example.withpet.ui.hospital.hospitalDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.withpet.core.BaseViewModel
import com.example.withpet.ui.hospital.hospitalComment.usecase.HospitalCommentRepository
import com.example.withpet.ui.hospital.hospitalDetail.usecase.HospitalStarRepository
import com.example.withpet.vo.hospital.HospitalReviewDTO
import com.example.withpet.vo.hospital.HospitalStarDTO
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HosDetailViewModel(val hospitalCommentRepository: HospitalCommentRepository , val hospitalStarRepository: HospitalStarRepository) : BaseViewModel() {

    private val _reviewList = MutableLiveData<ArrayList<HospitalReviewDTO>>()
    val reviewList: LiveData<ArrayList<HospitalReviewDTO>>
        get() = _reviewList

    private val _starData = MutableLiveData<HospitalStarDTO>()
    val starData: LiveData<HospitalStarDTO>
        get() = _starData

    fun getHospitalReviewData(hospitalUid : String){
        addDisposable(
            hospitalCommentRepository.getHospitalReviewData(hospitalUid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {t: ArrayList<HospitalReviewDTO>? ->
                    _reviewList.postValue(t)
                }
        )
    }

    fun getHospitalStarData(hospitalUid : String){
        addDisposable(
            hospitalStarRepository.getStarData(hospitalUid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {t: HospitalStarDTO? ->
                    _starData.postValue(t)
                }
        )
    }
}