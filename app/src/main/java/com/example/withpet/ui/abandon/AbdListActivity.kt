package com.example.withpet.ui.abandon

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.withpet.R
import com.example.withpet.core.BaseActivity
import com.example.withpet.databinding.AbdListActivityBinding
import com.example.withpet.ui.abandon.adapter.AbdAdapter
import com.example.withpet.ui.abandon.enums.SigunguEnum
import org.koin.androidx.viewmodel.ext.android.viewModel

class AbdListActivity : BaseActivity() {
    lateinit var binding: AbdListActivityBinding
    val viewModel: AbdViewModel by viewModel()

    val adapter = AbdAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.abd_list_activity)

        onInitBinding()
        onLoadOnce()
        onLoad()
    }

    private fun onInitBinding() {
        binding.code = SigunguEnum.전체.code
        binding.viewModel = viewModel
        binding.sigungu.setOnClickListener { AbdSelectSigunguDialog().show(supportFragmentManager, "시군구 선택") }
    }

    private fun onLoadOnce() {
        observeLiveData()

        binding.list.adapter = adapter
    }

    private fun onLoad() {
        viewModel.getAbandonAnimalList()
    }

    private fun observeLiveData() {
        viewModel.showProgress.observe(this, Observer { it?.let { progress -> if (progress) mActivity.showProgress() else mActivity.dismissProgress() } })
        viewModel.clear.observe(this, Observer {
            adapter.clear()
        })
        viewModel.abandonAnimalResult.observe(this, Observer {
            adapter.addAll(it)
        })
    }

}