package com.example.withpet.ui.hospitalComment.usecase.impl

import com.example.withpet.ui.hospitalComment.usecase.HospitalCommentRepository
import com.example.withpet.util.Const.COLECT_HOSPITAL
import com.example.withpet.util.Const.COLECT_REVIEW
import com.example.withpet.util.Const.COLECT_STAR
import com.example.withpet.util.Log
import com.example.withpet.vo.hospital.HospitalCommentDTO
import com.example.withpet.vo.hospital.HospitalStarDTO
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class HospitalCommentRepositoryImpl : HospitalCommentRepository {


    override fun putHospitalComment(hospitalUid : String , comment: HospitalCommentDTO) {
        FirebaseFirestore.getInstance().collection(COLECT_HOSPITAL).document(hospitalUid).collection(COLECT_REVIEW).document().set(comment).addOnCompleteListener {
            task ->
            if(task.isSuccessful) {
                Log.e("Hospital Comment push **success**")
            }else {
                Log.e("Hospital Comment push **failed**")
            }
        }
    }

    override fun putHospitalStar(hospitalUid: String, starPoint: Int) {

        FirebaseFirestore.getInstance().collection(COLECT_HOSPITAL).document(hospitalUid).collection(COLECT_STAR).document(COLECT_STAR).get().addOnCompleteListener {
            task ->

            if(task.isSuccessful){
                var document : DocumentSnapshot? = task.getResult()

                // 문서가 존재하지 않을때
                if(document == null || !document.exists()){
                    var star = HospitalStarDTO(avg = starPoint , sum = 1 , starOne = 0 , starTwo = 0 , starThree = 0 , starFour = 0 , starFive = 0)

                    when(starPoint){
                        1 -> star.starOne++
                        2 -> star.starTwo++
                        3 -> star.starThree++
                        4 -> star.starFour++
                        5 -> star.starFive++
                    }
                    FirebaseFirestore.getInstance().collection(COLECT_HOSPITAL).document(hospitalUid).collection(COLECT_STAR).document(COLECT_STAR).set(star).addOnCompleteListener {
                            task ->
                        if(task.isSuccessful) {
                            Log.e("Hospital star push **success** , case by document not exits")
                        }else {
                            Log.e("Hospital star push **failed** , case by document not exits")
                        }
                    }
                }
            }else {

            }
        }
    }





}