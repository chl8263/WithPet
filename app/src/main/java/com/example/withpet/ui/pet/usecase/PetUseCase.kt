package com.example.withpet.ui.pet.usecase

import com.example.withpet.util.Auth
import com.example.withpet.util.Log
import com.example.withpet.vo.pet.PetDTO
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.Single

interface PetUseCase {

    fun getPetList(): Single<List<PetDTO>>
    fun edit(petDTO: PetDTO): Single<Boolean>
}

class PetUseCaseImpl : PetUseCase {

    override fun getPetList(): Single<List<PetDTO>> {
        return Single.create { emitter ->
            val db = FirebaseFirestore.getInstance()
            val email = Auth.getEmail()
            if (email.isNullOrEmpty()) {
                throw Exception("로그인이 필요합니다.")
            } else {
                db.collection(PET_COLLECTION_PATH)
                        .document(email)
                        .collection(PET_LIST_COLLECTION_PATH)
                        .orderBy("createDate")
                        .get()
                        .addOnSuccessListener {
                            it?.let {
                                val petList = it.toObjects(PetDTO::class.java)
                                emitter.onSuccess(petList)
                            }
                        }.addOnFailureListener {
                            throw it
                        }
            }
        }
    }

    override fun edit(petDTO: PetDTO): Single<Boolean> {
        return Single.create { emitter ->
            val db = FirebaseFirestore.getInstance()
            val email = Auth.getEmail()

            if (email.isNullOrEmpty()) {
                throw Exception("로그인이 필요합니다.")
            } else {
                db.collection(PET_COLLECTION_PATH)
                        .document(email)
                        .collection(PET_LIST_COLLECTION_PATH)
                        .document(Auth.getPetListId(petDTO))
                        .set(petDTO)
                        .addOnCompleteListener {
                            Log.i("db collection isSuccessFul : ${it.isSuccessful}")
                            emitter.onSuccess(it.isSuccessful)
                        }
            }
        }
    }
}

const val PET_COLLECTION_PATH = "PET"
const val PET_LIST_COLLECTION_PATH = "PET_LIST"