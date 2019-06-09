package com.example.withpet.ui.join

import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.withpet.R
import com.example.withpet.core.BaseActivity
import com.example.withpet.databinding.JoinActivityBinding
import com.example.withpet.util.Log
import org.koin.androidx.viewmodel.ext.android.viewModel

class JoinActivity : BaseActivity() {

    lateinit var bb: JoinActivityBinding
    private val vm: JoinViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bb = DataBindingUtil.setContentView(this, R.layout.join_activity)
        bb.vm = vm

        initBinding()
    }

    private fun initBinding() {
        vm.isBack.observe(this, Observer { onBackPressed() })
        vm.isJoinSuccess.observe(this, Observer {
            it?.let { isSuccess ->
                if (isSuccess) {
                    Log.i("success")
                    showDialog(message = "회원가입이 완료되었습니다.\n로그인해주세요.", positiveListener = { _, _ ->
                        finish()
                    })
                } else {
                    showDialog(message = "회원가입실패..............?", positiveListener = { _, _ ->
                        finish()
                    })
                }
            }
        })
    }
}

