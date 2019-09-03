package com.example.withpet.ui.diary

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.withpet.R
import com.example.withpet.core.BaseActivity
import com.example.withpet.databinding.ActivityDiaryAddBinding

class DiaryAddActivity : BaseActivity() {

    lateinit var bb: ActivityDiaryAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bb = DataBindingUtil.setContentView(mActivity, R.layout.activity_diary_add)
    }
}