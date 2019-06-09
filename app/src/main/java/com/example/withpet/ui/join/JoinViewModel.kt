package com.example.withpet.ui.join

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.withpet.core.BaseViewModel
import com.example.withpet.ui.join.usecase.JoinUseCase
import com.example.withpet.util.LiveEvent
import com.example.withpet.util.Log

class JoinViewModel(private val joinUseCase: JoinUseCase) : BaseViewModel() {


    val email = ObservableField<String>()               // Email
    val password = ObservableField<String>()            // password

    private val _isBack = LiveEvent<Any>()              // backButton
    val isBack: LiveData<Any>
        get() = _isBack

    val isJoinSuccess = MediatorLiveData<Boolean>()     // Join 결과

    fun join() {
        val email = email.get()
        val password = password.get()
        if (email != null && password != null) {
            Log.i("email : $email, password: $password")
            isJoinSuccess.addSource(joinUseCase.join(email, password)) {
                isJoinSuccess.postValue(it)
            }
        }
    }

    fun back() {
        _isBack.call()
    }

}