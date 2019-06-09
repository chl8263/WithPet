package com.example.withpet.ui.login

import androidx.databinding.Observable
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.withpet.core.BaseViewModel
import com.example.withpet.ui.login.usecase.LoginUseCase
import com.example.withpet.util.LiveEvent
import com.example.withpet.util.Log
import com.example.withpet.util.Regular

class LoginViewModel(private val loginUseCase: LoginUseCase) : BaseViewModel() {

    val email = ObservableField<String>()               // email
    val password = ObservableField<String>()            // password
    val isEnable = ObservableBoolean(false)      // 버튼 활성화 여부

    val isLoginSuccess = MediatorLiveData<Boolean>()    // Login 여부

    private val _joinCall = LiveEvent<Any>()
    val joinCall: LiveData<Any>
        get() = _joinCall

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage


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
                if (it) {
                    isLoginSuccess.postValue(it)
                } else {

                    clear()
                }
            }
        }

    }

    private fun validation() {
        val isEmailEmpty = email.get().isNullOrEmpty()
        val isPasswordEmpty = password.get().isNullOrEmpty()
        val isBtnEnable = !isEmailEmpty && !isPasswordEmpty
        if (isBtnEnable) {
            email.get()?.let {
                // Email 정규식 Check
                val isEmail = Regular.checkEmail(it)
                isEnable.set(isEmail)
            }
        }
    }

    private fun clear() {
        _errorMessage.postValue("이메일과 비밀번호를 확인해주세요.")
        email.set("")
        password.set("")
    }
}