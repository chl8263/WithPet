package com.example.withpet.ui.abandon.usecase

import com.example.withpet.util.Auth
import com.example.withpet.util.Log
import com.example.withpet.vo.diary.DiaryDTO
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Single

interface DiaryUseCase {
    fun insert(diary: DiaryDTO): Single<Boolean>
}

class DiaryUseCaseImpl : DiaryUseCase {
    override fun insert(diary: DiaryDTO): Single<Boolean> {
        return Single.create { emitter ->
            val db = FirebaseFirestore.getInstance()
            val email = Auth.getEmail()

            if (email.isNullOrEmpty()) {
                throw Exception("로그인이 필요합니다.")
            } else {
                db.collection(DIARY_COLLECTION_PATH)
                        .document(email)
                        .collection(DIARY_LIST_COLLECTION_PATH)
                        .add(diary)
                        .addOnCompleteListener {
                            Log.i("db collection isSuccessFul : ${it.isSuccessful}")
                            emitter.onSuccess(it.isSuccessful)
                        }
            }
        }
    }
}


const val DIARY_COLLECTION_PATH = "DIARY"
const val DIARY_LIST_COLLECTION_PATH = "DIARY_LIST"