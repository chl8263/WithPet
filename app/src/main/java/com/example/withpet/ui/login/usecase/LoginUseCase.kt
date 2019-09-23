package com.example.withpet.ui.login.usecase

import com.example.withpet.util.Auth
import com.example.withpet.util.Log
import com.google.firebase.auth.FirebaseAuth
import io.reactivex.Single

interface LoginUseCase {
    fun login(email: String, password: String): Single<Boolean>
}

class LoginUseCaseImpl : LoginUseCase {

    private val auth = FirebaseAuth.getInstance()
    override fun login(email: String, password: String): Single<Boolean> {
        return Single.create { emitter ->
            auth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener {
                        Log.i(it.user.email)
                        it?.let {
                            Auth.login(it.user)
                            emitter.onSuccess(true)
                        }
                    }
                    .addOnFailureListener { throw it }
        }
    }

}