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
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class JoinViewModel(private val joinUseCase: JoinUseCase) : BaseViewModel() {


    val email = ObservableField<String>()              // Email
    val password = ObservableField<String>()           // password
    val passwordOk = ObservableField<String>()         // password 확인
    val nicName = ObservableField<String>()            // 닉네임

//    val isJoinSuccess = MediatorLiveData<Boolean>()    // Join 결과

    private val _joinSuccess = MutableLiveData<Boolean>()   // Join
    val joinSuccess: LiveData<Boolean>
        get() = _joinSuccess

    private val _errorMessage = MutableLiveData<String>()   // Error Message
    val errorMessage: LiveData<String>
        get() = _errorMessage

    private val _showProgress = MutableLiveData<Boolean>()   // Progress
    val showProgress: LiveData<Boolean>
        get() = _showProgress


    fun join() {
        if (validation()) return

        val email = email.get()
        val password = password.get()
        val passwordOk = passwordOk.get()
        val nicName = nicName.get()
        if (email != null && password != null && passwordOk != null && nicName != null) {
            Log.i("email : $email, password: $password")
            launch {
                joinUseCase.join(email, password, nicName)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe { _showProgress.postValue(true) }
                        .doOnSuccess { _showProgress.postValue(false) }
                        .doOnError { _showProgress.postValue(false) }
                        .subscribe({
                            _joinSuccess.postValue(it)
                        }, { exception ->
                            Log.e("join Error : ${exception.message}")
                            exception.printStackTrace()
                            _errorMessage.postValue(exception.message)
                        })
            }
        }
    }

    private fun validation(): Boolean {

        val email = email.get()
        val password = password.get()
        val passwordOk = passwordOk.get()
        val nicName = nicName.get()

        if (email.isNullOrEmpty()) {
            _errorMessage.postValue("이메일을 입력 해 주세요.")
            return true
        }

        if (password.isNullOrEmpty()) {
            _errorMessage.postValue("비밀번호를 입력 해 주세요.")
            return true
        }

        if (passwordOk.isNullOrEmpty()) {
            _errorMessage.postValue("비밀번호 확인을 입력 해 주세요.")
            return true
        }

        if (nicName.isNullOrEmpty()) {
            _errorMessage.postValue("닉네임을 입력 해 주세요.")
            return true
        }

        if (password != passwordOk) {
            _errorMessage.postValue("비밀번호가 일치 하지 않습니다. 다시 확인해 주세요.")
            return true
        }

        return false
    }
}