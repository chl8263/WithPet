package com.example.withpet.ui.diary

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.withpet.R
import com.example.withpet.core.BaseActivity
import com.example.withpet.databinding.ActivityDiaryDetailBinding
import com.example.withpet.vo.diary.DiaryDTO
import org.koin.androidx.viewmodel.ext.android.viewModel

class DiaryDetailActivity : BaseActivity() {

    lateinit var bb: ActivityDiaryDetailBinding
    private val vm: DiaryDetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bb = DataBindingUtil.setContentView(mActivity, R.layout.activity_diary_detail)
        bb.vm = vm
        onParseExtra()
    }

    private fun onParseExtra() {
        try {
            intent.getSerializableExtra(EXTRA.DIARY_DTO)?.let {
                val dto = it as DiaryDTO
                vm.init(dto)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun onLoadOnce() {

    }


    interface EXTRA {
        companion object {
            const val DIARY_DTO = "DIARY_DTO"
        }
    }
}