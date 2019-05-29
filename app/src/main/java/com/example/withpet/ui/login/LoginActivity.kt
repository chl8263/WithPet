package com.example.withpet.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.withpet.R
import com.example.withpet.core.BaseActivity
import com.example.withpet.databinding.ActivityLoginBinding
import com.example.withpet.ui.join.JoinActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : BaseActivity(){

    lateinit var binding : ActivityLoginBinding

    val viewModel : LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this , R.layout.activity_login)

        binding.viewModel = viewModel

        initBinding()

    }

    fun initBinding(){
        viewModel.moveJoinPage.observe(this, Observer {
            Log.e("aa","bbbbbb")
            startActivity(Intent(this, JoinActivity::class.java))
        })
    }
}
