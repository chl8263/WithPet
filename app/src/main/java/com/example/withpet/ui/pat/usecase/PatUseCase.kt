package com.example.withpet.ui.pat.usecase

import com.example.withpet.util.Auth
import com.example.withpet.util.Log
import com.example.withpet.vo.pat.PatDTO
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Single

interface PatUseCase {

    fun insert(patDTO: PatDTO): Single<Boolean>

}

class PatUseCaseImpl : PatUseCase {

    override fun insert(patDTO: PatDTO): Single<Boolean> {
        return Single.create { emitter ->
            val db = FirebaseFirestore.getInstance()
            val email = Auth.email
            email?.let { userEmail ->
                db.collection(PAT_COLLECTION_PATH)
                        .document(userEmail)
                        .set(patDTO)
                        .addOnCompleteListener {
                            Log.i("db collection isSuccessFul : ${it.isSuccessful}")
                            emitter.onSuccess(it.isSuccessful)
                        }

            }
        }
    }
}

const val PAT_COLLECTION_PATH = "PAT"