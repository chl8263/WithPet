package com.example.withpet.ui.join.usecase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.UserProfileChangeRequest
import io.reactivex.Single

interface JoinUseCase {
    fun join(email: String, password: String, nicName: String): Single<Boolean>
}


class JoinUseCaseImpl : JoinUseCase {

    private val auth = FirebaseAuth.getInstance()
    override fun join(email: String, password: String, nicName: String): Single<Boolean> {

        return Single.create { emitter ->
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            val profileUpdate = UserProfileChangeRequest.Builder().apply {
                                setDisplayName(nicName)
                            }.build()
                            user?.updateProfile(profileUpdate)?.addOnCompleteListener {
                                emitter.onSuccess(task.isSuccessful)
                            }
                        } else {
                            val exception = task.exception
                            exception?.let {
                                if (it is FirebaseAuthUserCollisionException) {
                                    emitter.onError(Exception("해당 아이디가 이미 있습니다."))
                                }
                                emitter.onError(it)
                            }
                            emitter.onError(Exception("알수없는 오류가 발생하였습니다."))
                        }
                    }
        }
    }

}