package com.example.withpet.ui.hospitalComment.usecase

import com.example.withpet.vo.hospital.HospitalReviewDTO
import io.reactivex.Observable

interface HospitalCommentRepository {

    fun putHospitalComment(hospitalUid : String, review : HospitalReviewDTO)

    fun putHospitalStar(hospitalUid : String , starPoint : Int)

    fun getHospitalReviewData(hospitalUid : String): Observable<ArrayList<HospitalReviewDTO>>
}