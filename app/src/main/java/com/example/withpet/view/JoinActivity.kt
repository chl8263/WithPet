package com.example.withpet.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.withpet.R
import com.example.withpet.core.BaseActivity
import com.example.withpet.core.BaseViewModel
import com.example.withpet.databinding.ActivityJoinBinding
import com.example.withpet.databinding.ActivityLoginBinding
import com.example.withpet.viewModel.JoinViewModel
import com.example.withpet.viewModel.LoginViewModel
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class JoinActivity : BaseActivity() {

    lateinit var binding : ActivityJoinBinding

    val viewModel : JoinViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this , R.layout.activity_join)

        binding.viewModel = viewModel

        initBinding()
    }

    fun initBinding(){
        viewModel.isBack.observe(this , Observer {
            finish()
        })

        viewModel.isJoinState.observe(this, Observer {
            Log.e("sibal","sibal")
            if(viewModel.isJoinState.value == true){
                Log.e("sibal","true")
                Toast.makeText(this,"join success",Toast.LENGTH_SHORT)
            }else {
                Log.e("sibal","false")
                Toast.makeText(this,"join failed",Toast.LENGTH_SHORT)
            }
        })


    }
}

