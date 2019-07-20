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
                    var starDto = HospitalStarDTO(avg = starPoint , sum = 0 , starTotalCount = 1 , starOne = 0 , starTwo = 0 , starThree = 0 , starFour = 0 , starFive = 0)

                    when(starPoint){
                        1 -> {
                            starDto.starOne++
                            starDto.sum = 1
                        }
                        2 -> {
                            starDto.starTwo++
                            starDto.sum = 2
                        }
                        3 -> {
                            starDto.starThree++
                            starDto.sum = 3
                        }
                        4 -> {
                            starDto.starFour++
                            starDto.sum = 4
                        }
                        5 -> {
                            starDto.starFive++
                            starDto.sum = 5
                        }
                    }
                    FirebaseFirestore.getInstance().collection(COLECT_HOSPITAL).document(hospitalUid).collection(COLECT_STAR).document(COLECT_STAR).set(starDto).addOnCompleteListener {
                            task ->
                        if(task.isSuccessful) {
                            Log.e("Hospital star push **success** , case by document not exits")
                        }else {
                            Log.e("Hospital star push **failed** , case by document not exits")
                        }
                    }
                }else { // 문서가 존재할 때
                    var starDto = task.result as HospitalStarDTO

                    when(starPoint){
                        1 -> {
                            starDto.starOne++
                            starDto.sum += 1
                        }
                        2 -> {
                            starDto.starTwo++
                            starDto.sum += 2
                        }
                        3 -> {
                            starDto.starThree++
                            starDto.sum += 3
                        }
                        4 -> {
                            starDto.starFour++
                            starDto.sum += 4
                        }
                        5 -> {
                            starDto.starFive++
                            starDto.sum += 5
                        }
                    }

                    starDto.starTotalCount++
                    starDto.avg = starDto.sum / starDto.starTotalCount

                    FirebaseFirestore.getInstance().collection(COLECT_HOSPITAL).document(hospitalUid).collection(COLECT_STAR).document(COLECT_STAR).set(starDto).addOnCompleteListener {
                            task ->
                        if(task.isSuccessful) {
                            Log.e("Hospital star push **success** , case by document not exits")
                        }else {
                            Log.e("Hospital star push **failed** , case by document not exits")
                        }
                    }
                }
            }
        }
    }





}