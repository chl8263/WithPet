package com.example.withpet.ui.diary

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.withpet.R
import com.example.withpet.core.BaseActivity
import com.example.withpet.databinding.ActivityDiaryListBinding

class DiaryListActivity : BaseActivity() {


    lateinit var bb: ActivityDiaryListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bb = DataBindingUtil.setContentView(mActivity, R.layout.activity_diary_list)
        bb.add.setOnClickListener {
            startActivity(Intent(mActivity, DiaryAddActivity::class.java))
        }
    }
}