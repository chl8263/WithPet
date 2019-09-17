package com.example.withpet.ui.join

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.withpet.R
import com.example.withpet.core.BaseActivity
import com.example.withpet.databinding.JoinActivityBinding
import com.example.withpet.ui.main.MainActivity
import com.example.withpet.ui.pat.PatAddActivity
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
        vm.joinSuccess.observe(this, Observer {
            it?.let { isSuccess ->
                if (isSuccess) {
                    showDialog(message = "회원가입이 완료되었습니다.\n애완동물을 등록하시겠습니까?",
                            positiveButtonText = "확인",
                            positiveListener = { _, _ -> startActivityForResult(Intent(mContext, PatAddActivity::class.java), REQ_PAT_ADD) },
                            negativeButtonText = "취소",
                            negativeListener = { _, _ ->
                                startActivity(Intent(this, MainActivity::class.java)
                                        .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
                            })
                } else {
                    showDialog(message = "회원가입이 실패하였습니다.",
                            positiveButtonText = "확인",
                            positiveListener = { _, _ -> finish() })
                }
            }
        })

        vm.errorMessage.observe(mActivity, Observer { errorMessage ->
            showDialog(message = errorMessage, positiveButtonText = "확인")
        })

        vm.showProgress.observe(mActivity, Observer { it?.let { progress -> if (progress) showProgress() else dismissProgress() } })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQ_PAT_ADD) {
            startActivity(Intent(this, MainActivity::class.java)
                    .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK))
        }
    }


    companion object {
        private const val REQ_START = 100
        private const val REQ_PAT_ADD = REQ_START
    }
}

