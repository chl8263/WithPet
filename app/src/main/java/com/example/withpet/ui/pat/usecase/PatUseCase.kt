package com.example.withpet.ui.pat.usecase

import com.example.withpet.util.Auth
import com.example.withpet.util.Log
import com.example.withpet.vo.pat.PatDTO
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Single

interface PatUseCase {

    fun getPatList(): Single<List<PatDTO>>

    fun insert(patDTO: PatDTO): Single<Boolean>
}

class PatUseCaseImpl : PatUseCase {
    override fun getPatList(): Single<List<PatDTO>> {
        return Single.create { emitter ->
            val db = FirebaseFirestore.getInstance()
            val email = Auth.email
            if (email.isNullOrEmpty()) {
                throw Exception("로그인이 필요합니다.")
            } else {
                db.collection(PAT_COLLECTION_PATH)
                        .document(email)
                        .collection(PAT_LIST_COLLECTION_PATH)
                        .get()
                        .addOnSuccessListener {
                            val patList = it.toObjects(PatDTO::class.java)
                            emitter.onSuccess(patList)
                        }.addOnFailureListener {
                            emitter.onError(it)
                        }
            }
        }
    }

    override fun insert(patDTO: PatDTO): Single<Boolean> {
        return Single.create { emitter ->
            val db = FirebaseFirestore.getInstance()
            val email = Auth.email

            if (email.isNullOrEmpty()) {
                throw Exception("로그인이 필요합니다.")
            } else {
                db.collection(PAT_COLLECTION_PATH)
                        .document(email)
                        .collection(PAT_LIST_COLLECTION_PATH)
                        .add(patDTO)
                        .addOnCompleteListener {
                            Log.i("db collection isSuccessFul : ${it.isSuccessful}")
                            emitter.onSuccess(it.isSuccessful)
                        }
            }
        }
    }
}

const val PAT_COLLECTION_PATH = "PAT"
const val PAT_LIST_COLLECTION_PATH = "PAT_LIST"