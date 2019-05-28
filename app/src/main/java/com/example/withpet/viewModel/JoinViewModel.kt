package com.example.withpet.viewModel

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.withpet.core.BaseViewModel
import com.example.withpet.util.LiveEvent
import com.example.withpet.util.SingleLiveEvent
import com.google.firebase.auth.FirebaseAuth

class JoinViewModel : BaseViewModel() {

    val auth = FirebaseAuth.getInstance()

     var _emailText = MutableLiveData<String>()
     var _passwordText = MutableLiveData<String>()

    private val _isBack = LiveEvent<Any>()
    val isBack : LiveData<Any>
        get() = _isBack

    private val _isJoinState = LiveEvent<Any>()
    val isJoinState : LiveData<Any>
        get() = _isJoinState


    fun back(){
        _isBack.call()
    }

    fun joinToFireBase(){
        auth?.let {
            auth.createUserWithEmailAndPassword(_emailText.value.toString()
                , _passwordText.value.toString()).addOnCompleteListener {
                task ->
                if(task.isSuccessful){
                    _isJoinState.setValue(true)
                }else {
                    _isJoinState.setValue(false)
                }
            }
        }
    }
}