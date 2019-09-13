package com.example.withpet.ui.hospital.hospitalDetail.usecase.impl

import com.example.withpet.ui.hospital.hospitalDetail.usecase.HospitalStarRepository
import com.example.withpet.util.Const
import com.example.withpet.vo.hospital.HospitalStarDTO
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Observable

class HospitalStarRepositoryImpl : HospitalStarRepository {

    private val db = FirebaseFirestore.getInstance()

    override fun getStarData(hospitalUid: String): Observable<HospitalStarDTO> {
        return Observable.create {
                emitter ->
            db.collection(Const.COLECT_HOSPITAL).document(hospitalUid).collection(Const.COLECT_STAR).document(Const.COLECT_STAR).addSnapshotListener { documentSnapshot, firebaseFirestoreException ->
                if(documentSnapshot == null) return@addSnapshotListener

                if(documentSnapshot.exists()) {
                    emitter.onNext(documentSnapshot.toObject(HospitalStarDTO::class.java)!!)
                }

            }
        }
    }
}