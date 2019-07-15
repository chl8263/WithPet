package com.example.withpet.ui.hospitalComment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.withpet.core.BaseViewModel
import com.example.withpet.ui.hospitalComment.usecase.HospitalCommentRepository
import com.example.withpet.vo.LocationVO
import com.example.withpet.vo.hospital.HospitalCommentDTO
import com.example.withpet.vo.hospital.HospitalSearchDTO

class HosCommentViewModel(val hospitalCommentRepository: HospitalCommentRepository) : BaseViewModel() {

    //----------------- s : LiveData ------------------

    private val _currentLocation = MutableLiveData<LocationVO>()
    val currentLocation: LiveData<LocationVO>
        get() = _currentLocation

    //----------------- e : LiveData ------------------

    fun putHospitalComment( hospitalUid : String , comment : HospitalCommentDTO) {
        hospitalCommentRepository.putHospitalComment( hospitalUid , comment )
    }
}