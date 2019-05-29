package com.example.withpet.ui.join

import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.withpet.R
import com.example.withpet.core.BaseActivity
import com.example.withpet.databinding.ActivityJoinBinding
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
            if(viewModel.isJoinState.value == true){
                Toast.makeText(this,"join success",Toast.LENGTH_SHORT).show()
            }else {
                Toast.makeText(this,"join failed",Toast.LENGTH_SHORT).show()
            }
        })
    }
}

