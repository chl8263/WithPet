package com.example.withpet.ui.hospital.usecase.impl

import com.example.withpet.ui.hospital.usecase.HospitalRepository
import com.example.withpet.util.Const.COLECT_HOSPITAL
import com.example.withpet.util.Const.COLECT_HOSPITAL_NAME
import com.example.withpet.util.Log

import com.example.withpet.vo.HospitalSearchDTO
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import io.reactivex.Observable

class HospitalRepositoryImpl : HospitalRepository {

    val firestore : FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    override fun getHospitalSearchData(searchValue: String): Observable<ArrayList<HospitalSearchDTO>> {
        return Observable.create {
            emitter ->

            firestore.collection(COLECT_HOSPITAL).orderBy(COLECT_HOSPITAL_NAME).startAt(searchValue).endAt(searchValue+'\uf8ff').get().addOnCompleteListener {
                task: Task<QuerySnapshot> ->

                if(task.isSuccessful) {
                    Log.e(task.result!!.documents)
                    emitter.onNext(task.result!!.documents as ArrayList<HospitalSearchDTO>)
                }

            }
        }
    }
}