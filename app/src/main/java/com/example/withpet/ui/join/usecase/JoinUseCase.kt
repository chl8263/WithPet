package com.example.withpet.ui.join.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth

interface JoinUseCase {
    fun join(email: String, password: String): LiveData<Boolean>
}


class JoinUseCaseImpl : JoinUseCase {

    private val auth = FirebaseAuth.getInstance()
    override fun join(email: String, password: String): LiveData<Boolean> {
        val isJoinSuccess = MutableLiveData<Boolean>()
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    isJoinSuccess.postValue(task.isSuccessful)
                }

        return isJoinSuccess
    }

}