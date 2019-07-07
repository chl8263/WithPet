package com.example.withpet.ui.hospitalComment

import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.withpet.core.BaseFragment
import com.example.withpet.databinding.FragmentHosDetailBinding
import com.example.withpet.databinding.FragmentHospitalBinding
import com.example.withpet.ui.hospital.HospitalFragment
import com.example.withpet.ui.hospital.HospitalViewModel
import com.example.withpet.ui.hospital.adapter.HospitalHistorySearchRecyclerViewAdapter
import com.example.withpet.ui.hospital.adapter.HospitalSearchRecyclerViewAdapter
import com.example.withpet.ui.hospitalDetail.HosDetailViewModel
import com.example.withpet.util.Const.HOSPITAL_DETAIL_DATA
import com.example.withpet.util.Log
import com.example.withpet.vo.HospitalSearchDTO
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.hospital_detail_fragment.*
import kotlinx.android.synthetic.main.hospital_detail_fragment.view.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class HosCommentFragment : DialogFragment() {

    lateinit var binding: com.example.withpet.databinding.FragmentHosCommentBinding
    val viewModel: HosCommentViewModel by viewModel()

    companion object {
        fun newInstance(): HosCommentFragment {
            val args = Bundle()
            val fragment = HosCommentFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, com.example.withpet.R.layout.hos_comment_fargment, container, false)
        binding.viewModel = viewModel

        initView(binding.root)

        return binding.root
    }

    private fun initView(view : View){


    }


}