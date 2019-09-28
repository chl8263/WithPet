package com.example.withpet.ui.diary

import android.os.Bundle
import com.example.withpet.core.BaseActivity
import com.example.withpet.databinding.ActivityDiaryDetailBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class DiaryDetailActivity : BaseActivity() {

    lateinit var bb: ActivityDiaryDetailBinding
    private val vm: DiaryDetailViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}