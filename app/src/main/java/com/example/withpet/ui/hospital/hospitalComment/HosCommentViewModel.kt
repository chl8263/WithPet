package com.example.withpet.ui.hospital.hospitalComment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.withpet.core.BaseViewModel
import com.example.withpet.ui.hospital.hospitalComment.usecase.HospitalCommentRepository
import com.example.withpet.vo.hospital.HospitalReviewDTO
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HosCommentViewModel(val hospitalCommentRepository: HospitalCommentRepository) : BaseViewModel() {

    private val _isPutComment = MutableLiveData<Boolean>()
    val isPutComment: LiveData<Boolean>
        get() = _isPutComment

    fun putHospitalComment(hospitalUid : String, review : HospitalReviewDTO) {
        addDisposable(
            hospitalCommentRepository.putHospitalComment(hospitalUid , review)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {t: Boolean ->
                    _isPutComment.postValue(t)
                }
        )
        hospitalCommentRepository.putHospitalComment(hospitalUid , review)
    }

    fun putHospitalStar(hospitalUid : String , starPoint: Int) {
        hospitalCommentRepository.putHospitalStar(hospitalUid , starPoint)
    }
}