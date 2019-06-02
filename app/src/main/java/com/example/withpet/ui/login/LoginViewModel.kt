package com.example.withpet.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.withpet.core.BaseViewModel
import com.example.withpet.util.LiveEvent
import com.example.withpet.util.SingleLiveEvent

class LoginViewModel : BaseViewModel() {

     var _emailText = MutableLiveData<String>()
     var _passwordText = MutableLiveData<String>()

    private val _isMoveMainPage = SingleLiveEvent<Any>()
    val isMoveMainPage : LiveData<Any>
        get() = _isMoveMainPage

    val moveJoinPage = LiveEvent<Any>()

    fun moveJoinPage(){
        Log.e("aa","aa")
        moveJoinPage.call()
    }
}