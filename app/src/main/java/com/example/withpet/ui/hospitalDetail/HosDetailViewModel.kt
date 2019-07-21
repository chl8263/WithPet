package com.example.withpet.ui.hospitalDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.withpet.core.BaseViewModel
import com.example.withpet.ui.hospitalComment.usecase.HospitalCommentRepository
import com.example.withpet.vo.LocationVO
import com.example.withpet.vo.hospital.HospitalReviewDTO
import com.example.withpet.vo.hospital.HospitalSearchDTO
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HosDetailViewModel(val hospitalCommentRepository: HospitalCommentRepository) : BaseViewModel() {

    //----------------- LiveData ------------------

    private val _reviewList = MutableLiveData<ArrayList<HospitalReviewDTO>>()
    val reviewList: LiveData<ArrayList<HospitalReviewDTO>>
        get() = _reviewList

    //----------------------------------------------

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
}