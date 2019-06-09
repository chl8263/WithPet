package com.example.withpet.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.withpet.core.BaseActivity
import com.example.withpet.databinding.ActivityLoginBinding
import com.example.withpet.ui.join.JoinActivity
import com.example.withpet.ui.main.MainActivity
import com.example.withpet.util.Log
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginActivity : BaseActivity() {

    lateinit var bb: ActivityLoginBinding

    val vm: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bb = DataBindingUtil.setContentView(this, com.example.withpet.R.layout.activity_login)
        bb.vm = vm

        initBinding()
    }

    private fun initBinding() {
        vm.joinCall.observe(this, Observer {
            startActivity(Intent(this, JoinActivity::class.java))
        })

        vm.isLoginSuccess.observe(this, Observer {
            it?.let { isLoginSuccess ->
                Log.i(isLoginSuccess)
                if (isLoginSuccess) {
                    startActivity(Intent(this, MainActivity::class.java)
                            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
                }
            }
        })

        vm.errorMessage.observe(this, Observer {
            it?.let { errorMessage ->
                showDialog(message = errorMessage, positiveListener = { _, _ ->
                    bb.emailEt.requestFocus()
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.showSoftInput(bb.emailEt, 0)
                })
            }
        })
    }
}
