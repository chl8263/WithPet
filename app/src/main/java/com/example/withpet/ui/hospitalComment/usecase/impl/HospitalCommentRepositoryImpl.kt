package com.example.withpet.ui.hospitalComment.usecase.impl

import com.example.withpet.ui.hospitalComment.usecase.HospitalCommentRepository
import com.example.withpet.util.Const.COLECT_HOSPITAL
import com.example.withpet.util.Const.COLECT_REVIEW
import com.example.withpet.vo.hospital.HospitalCommentDTO
import com.google.firebase.firestore.FirebaseFirestore

class HospitalCommentRepositoryImpl : HospitalCommentRepository {

    override fun putHospitalComment(hospitalUid : String , comment: HospitalCommentDTO) {
        FirebaseFirestore.getInstance().collection(COLECT_HOSPITAL).document(hospitalUid).collection(COLECT_REVIEW).document().set(comment)
    }



}