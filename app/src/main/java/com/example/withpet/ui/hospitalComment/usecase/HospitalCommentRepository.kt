package com.example.withpet.ui.hospitalComment.usecase

import com.example.withpet.vo.hospital.HospitalCommentDTO
import com.example.withpet.vo.hospital.HospitalSearchDTO
import io.reactivex.Observable
import kotlin.collections.ArrayList

interface HospitalCommentRepository {

    fun putHospitalComment(hospitalUid : String , comment : HospitalCommentDTO)

    fun putHospitalStar(hospitalUid : String , starPoint : Int)
}