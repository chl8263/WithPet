package com.example.withpet.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.withpet.core.BaseViewModel
import com.example.withpet.util.SingleLiveEvent

class LoginViewModel : BaseViewModel() {

    private var _emailText = MutableLiveData<String>()
    private var _passwordText = MutableLiveData<String>()

    private val _isMoveMainPage = SingleLiveEvent<Any>()
    val isMoveMainPage : LiveData<Any>
        get() = _isMoveMainPage

    val moveJoinPage = SingleLiveEvent<Any>()

    fun moveLoginsPage(){
        moveJoinPage.call()
    }
}