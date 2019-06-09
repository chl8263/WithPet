package com.example.withpet.ui.login

import androidx.databinding.Observable
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.withpet.core.BaseViewModel
import com.example.withpet.ui.login.usecase.LoginUseCase
import com.example.withpet.util.LiveEvent
import com.example.withpet.util.Log

class LoginViewModel(private val loginUseCase: LoginUseCase) : BaseViewModel() {

    val email = ObservableField<String>()               // email
    val password = ObservableField<String>()            // password
    val isEnable = ObservableBoolean(false)      // 버튼 활성화 여부

    val isLoginSuccess = MediatorLiveData<Boolean>()    // Login 여부

    private val _joinCall = LiveEvent<Any>()
    val joinCall: LiveData<Any>
        get() = _joinCall


    init {
        email.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                validation()
            }
        })

        password.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                validation()
            }
        })
    }

    fun joinPage() {
        _joinCall.call()
    }

    fun login() {
        val email = email.get()
        val password = password.get()
        if (email != null && password != null) {
            Log.i("email : $email, password: $password")
            isLoginSuccess.addSource(loginUseCase.login(email, password)) {
                isLoginSuccess.postValue(it)
            }
        }

    }

    private fun validation() {
        val isEmailEmpty = email.get().isNullOrEmpty()
        val isPasswordEmpty = password.get().isNullOrEmpty()
        val isBtnEnable = !isEmailEmpty && !isPasswordEmpty
        isEnable.set(isBtnEnable)
    }
}