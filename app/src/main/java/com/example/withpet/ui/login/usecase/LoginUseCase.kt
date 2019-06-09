package com.example.withpet.ui.login.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth

interface LoginUseCase {
    fun login(email: String, password: String): LiveData<Boolean>
}

class LoginUseCaseImpl : LoginUseCase {

    private val auth = FirebaseAuth.getInstance()
    override fun login(email: String, password: String): LiveData<Boolean> {
        val isLoginSuccess = MutableLiveData<Boolean>()
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    isLoginSuccess.postValue(task.isSuccessful)
                }

        return isLoginSuccess
    }

}