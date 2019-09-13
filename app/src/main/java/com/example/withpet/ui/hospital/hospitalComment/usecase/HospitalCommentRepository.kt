package com.example.withpet.ui.hospital.hospitalComment.usecase

import com.example.withpet.vo.hospital.HospitalReviewDTO
import io.reactivex.Observable

interface HospitalCommentRepository {

    fun putHospitalComment(hospitalUid : String, review : HospitalReviewDTO) : Observable<Boolean>

    fun putHospitalStar(hospitalUid : String , starPoint : Int)

    fun getHospitalReviewData(hospitalUid : String): Observable<ArrayList<HospitalReviewDTO>>
}