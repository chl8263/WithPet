package com.example.withpet.ui.hospitalComment

import com.example.withpet.core.BaseViewModel
import com.example.withpet.ui.hospitalComment.usecase.HospitalCommentRepository
import com.example.withpet.vo.hospital.HospitalReviewDTO

class HosCommentViewModel(val hospitalCommentRepository: HospitalCommentRepository) : BaseViewModel() {

    //----------------- s : LiveData ------------------

   /* private val _currentLocation = MutableLiveData<LocationVO>()
    val currentLocation: LiveData<LocationVO>
        get() = _currentLocation*/

    //----------------- e : LiveData ------------------

    fun putHospitalComment(hospitalUid : String, review : HospitalReviewDTO) {
        hospitalCommentRepository.putHospitalComment(hospitalUid , review)
    }

    fun putHospitalStar(hospitalUid : String , starPoint: Int) {
        hospitalCommentRepository.putHospitalStar(hospitalUid , starPoint)
    }
}