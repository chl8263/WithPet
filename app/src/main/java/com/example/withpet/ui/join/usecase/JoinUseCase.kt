package com.example.withpet.ui.join.usecase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.withpet.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.UserProfileChangeRequest

interface JoinUseCase {
    fun join(email: String, password: String, nicName: String): LiveData<Boolean>
}


class JoinUseCaseImpl : JoinUseCase {

    private val auth = FirebaseAuth.getInstance()
    override fun join(email: String, password: String, nicName: String): LiveData<Boolean> {
        val isJoinSuccess = MutableLiveData<Boolean>()
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val user = auth.currentUser
                        val profileUpdate = UserProfileChangeRequest.Builder().apply {
                            setDisplayName(nicName)
                        }.build()

                        user?.updateProfile(profileUpdate)?.addOnCompleteListener {
                            Log.i("task successFul : ${it.isSuccessful}")
                            isJoinSuccess.postValue(task.isSuccessful)
                        }?.run {
                            Log.i("Run Call")
                        }
                    } else {
                        val exception = task.exception
                        exception?.let {
                            Log.e(it)
                            if (it is FirebaseAuthUserCollisionException) {
                                Log.e("해당 아이디가 이미 있습니다.")
                            }
                        }
                        isJoinSuccess.postValue(false)
                    }
                }

        return isJoinSuccess
    }

}