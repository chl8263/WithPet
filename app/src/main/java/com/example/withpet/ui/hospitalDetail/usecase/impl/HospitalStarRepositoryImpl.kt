package com.example.withpet.ui.hospitalDetail.usecase.impl

import com.example.withpet.ui.hospitalDetail.usecase.HospitalStarRepository
import com.example.withpet.util.Const
import com.example.withpet.vo.hospital.HospitalReviewDTO
import com.example.withpet.vo.hospital.HospitalStarDTO
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import io.reactivex.Observable

class HospitalStarRepositoryImpl : HospitalStarRepository {

    private val db = FirebaseFirestore.getInstance()

    override fun getStarData(hospitalUid: String): Observable<HospitalStarDTO> {
        return Observable.create {
                emitter ->
            db.collection(Const.COLECT_HOSPITAL).document(hospitalUid).collection(Const.COLECT_STAR).document(Const.COLECT_STAR).addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
                if(documentSnapshot == null) return@addSnapshotListener

                    emitter.onNext(documentSnapshot.toObject(HospitalStarDTO::class.java)!!)
            }
        }
    }
}