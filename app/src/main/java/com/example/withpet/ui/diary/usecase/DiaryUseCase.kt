package com.example.withpet.ui.diary.usecase

import com.example.withpet.util.Auth
import com.example.withpet.util.Log
import com.example.withpet.vo.diary.DiaryDTO
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Single

interface DiaryUseCase {
    fun getDiaryList(): Single<List<DiaryDTO>>
    fun insert(diary: DiaryDTO): Single<Boolean>
}

class DiaryUseCaseImpl : DiaryUseCase {
    override fun getDiaryList(): Single<List<DiaryDTO>> {
        return Single.create { emitter ->
            val db = FirebaseFirestore.getInstance()
            val email = Auth.getEmail()
            if (email.isNullOrEmpty()) {
                emitter.onError(Exception("로그인이 필요합니다."))
            } else {
                db.collection(DIARY_COLLECTION_PATH)
                        .document(email)
                        .collection(DIARY_LIST_COLLECTION_PATH)
                        .orderBy("createDate")
                        .get()
                        .addOnSuccessListener {
                            it?.let {
                                val diaryList = it.toObjects(DiaryDTO::class.java)
                                emitter.onSuccess(diaryList)
                            }
                        }.addOnFailureListener {
                            emitter.onError(it)
                        }
            }
        }
    }

    override fun insert(diary: DiaryDTO): Single<Boolean> {
        return Single.create { emitter ->
            val db = FirebaseFirestore.getInstance()
            val email = Auth.getEmail()

            if (email.isNullOrEmpty()) {
                emitter.onError(Exception("로그인이 필요합니다."))
            } else {
                db.collection(DIARY_COLLECTION_PATH)
                        .document(email)
                        .collection(DIARY_LIST_COLLECTION_PATH)
                        .document(Auth.getDiaryListId(diary))
                        .set(diary)
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