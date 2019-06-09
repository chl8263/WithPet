package com.example.withpet.ui.my

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.withpet.R
import com.example.withpet.core.BaseFragment
import com.example.withpet.databinding.FragmentMyBinding

class MyFragment : BaseFragment() {

    lateinit var bb: FragmentMyBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        bb = DataBindingUtil.inflate(inflater, R.layout.fragment_my, container, false)
        return bb.root
    }

}