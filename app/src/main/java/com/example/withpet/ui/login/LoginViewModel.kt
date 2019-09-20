package com.example.withpet.ui.login

import androidx.databinding.Observable
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.example.withpet.core.BaseViewModel
import com.example.withpet.ui.login.usecase.LoginUseCase
import com.example.withpet.util.*

class LoginViewModel(private val loginUseCase: LoginUseCase) : BaseViewModel() {

    val email = ObservableField<String>()               // email
    val password = ObservableField<String>()            // password
    val isEnable = ObservableBoolean(false)      // 버튼 활성화 여부

    private val _joinCall = LiveEvent<Any>()
    val joinCall: LiveData<Any>
        get() = _joinCall

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    private val _showProgress = MutableLiveData<Boolean>()   // Progress
    val showProgress: LiveData<Boolean>
        get() = _showProgress

    private val _loginSuccess = MutableLiveData<Boolean>()   // Join
    val loginSuccess: LiveData<Boolean>
        get() = _loginSuccess

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
        if (!email.isNullOrEmpty() && !password.isNullOrEmpty()) {
            Log.i("email : $email, password: $password")
            launch {
                loginUseCase.login(email, password)
                        .with()
                        .progress(_showProgress)
                        .subscribe({ _loginSuccess.postValue(it) }, {
                            _errorMessage.postValue(it.message)
                            clear()
                        })
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
//        email.set("")
        password.set("")
    }
}