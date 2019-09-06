package com.example.withpet.ui.my

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.withpet.R
import com.example.withpet.core.BaseFragment
import com.example.withpet.databinding.FragmentMyBinding
import com.example.withpet.ui.diary.DiaryListActivity

class MyFragment : BaseFragment() {

    lateinit var bb: FragmentMyBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        bb = DataBindingUtil.inflate(inflater, R.layout.fragment_my, container, false)
        return bb.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bb.diary.setOnClickListener {
            startActivity(Intent(mActivity, DiaryListActivity::class.java))
        }
    }

}