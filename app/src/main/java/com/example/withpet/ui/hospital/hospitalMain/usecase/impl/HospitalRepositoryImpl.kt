package com.example.withpet.ui.hospital.hospitalMain.usecase.impl

import com.example.withpet.ui.hospital.hospitalMain.usecase.HospitalRepository
import com.example.withpet.util.Const.COLECT_HOSPITAL
import com.example.withpet.util.Const.COLECT_HOSPITAL_NAME

import com.example.withpet.vo.hospital.HospitalSearchDTO
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import io.reactivex.Observable

class HospitalRepositoryImpl : HospitalRepository {

    val firestore : FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    var list = ArrayList<HospitalSearchDTO>()

    override fun getHospitalSearchData(searchValue: String): Observable<ArrayList<HospitalSearchDTO>> {
        return Observable.create {
            emitter ->
            firestore.collection(COLECT_HOSPITAL).orderBy(COLECT_HOSPITAL_NAME).startAt(searchValue).endAt(searchValue+'\uf8ff').get().addOnCompleteListener {
                task: Task<QuerySnapshot> ->

                if(task.isSuccessful) {
                    list.clear()
                    for(snapshot in task.result?.documents!!){
                        list.add(snapshot.toObject(HospitalSearchDTO::class.java)!!)
                    }

                    emitter.onNext(list)
                }

            }
        }
    }

    override fun getHospitalSubLocation(searchValue: String): Observable<ArrayList<HospitalSearchDTO>> {
        return Observable.create {
                emitter ->
            firestore.collection(COLECT_HOSPITAL).orderBy(COLECT_HOSPITAL_NAME)
                .whereEqualTo("gu",searchValue).get().addOnCompleteListener {
                    task: Task<QuerySnapshot> ->

                if(task.isSuccessful) {
                    list.clear()
                    for(snapshot in task.result?.documents!!){
                        list.add(snapshot.toObject(HospitalSearchDTO::class.java)!!)
                    }

                    emitter.onNext(list)
                }
            }
        }
    }
}