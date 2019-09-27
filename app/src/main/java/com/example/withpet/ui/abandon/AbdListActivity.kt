package com.example.withpet.ui.abandon

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.withpet.R
import com.example.withpet.core.BaseActivity
import com.example.withpet.databinding.AbdListActivityBinding
import com.example.withpet.ui.abandon.enums.eSigungu
import com.example.withpet.util.Log
import org.koin.androidx.viewmodel.ext.android.viewModel

class AbdListActivity : BaseActivity() {
    lateinit var binding: AbdListActivityBinding
    val viewModel: AbdViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.abd_list_activity)

        observeLiveData()

        val enumValues = listOf(eSigungu.values())
        val sb = StringBuilder()
        enumValues[0].forEach {
            sb.append("${it.name} : ${it.code}\n")
        }
        binding.test.text = sb

        viewModel.getList(eSigungu.가정보호, 1)
    }

    private fun observeLiveData() {
        viewModel.showProgress.observe(this, Observer { it?.let { progress -> if (progress) mActivity.showProgress() else mActivity.dismissProgress() } })
        viewModel.abandonAnimalList.observe(this, Observer {
            Log.w(it)
        })
    }

}