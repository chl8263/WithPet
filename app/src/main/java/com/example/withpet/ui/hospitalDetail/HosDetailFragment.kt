package com.example.withpet.ui.hospitalDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.example.withpet.core.BaseFragment
import com.example.withpet.databinding.FragmentHosDetailBinding
import com.example.withpet.databinding.FragmentHospitalBinding
import com.example.withpet.ui.hospital.HospitalFragment
import com.example.withpet.ui.hospital.HospitalViewModel
import com.example.withpet.ui.hospital.adapter.HospitalHistorySearchRecyclerViewAdapter
import com.example.withpet.ui.hospital.adapter.HospitalSearchRecyclerViewAdapter
import com.example.withpet.util.Const.HOSPITAL_DETAIL_DATA
import com.example.withpet.util.Log
import com.example.withpet.vo.HospitalSearchDTO
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.hospital_detail_fragment.view.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class HosDetailFragment : DialogFragment() {

    //private val hospitalAdapter: HospitalSearchRecyclerViewAdapter by inject()
    //private val historyAdapter: HospitalHistorySearchRecyclerViewAdapter by inject()

    lateinit var binding: FragmentHosDetailBinding
    val viewModel: HosDetailViewModel by viewModel()

    lateinit var hos_detail_data : HospitalSearchDTO


    companion object {
        fun newInstance(): HosDetailFragment {
            val args = Bundle()
            val fragment = HosDetailFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, com.example.withpet.R.layout.hospital_detail_fragment, container, false)
        binding.viewModel = viewModel

        initView(binding.root)

        return binding.root
    }

    private fun initView(view : View){

        hos_detail_data = arguments?.getSerializable(HOSPITAL_DETAIL_DATA) as HospitalSearchDTO

        // 초기 셋팅
        setTab_1()

        view.hos_detail_fragment_tab.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
            override fun onTabReselected(p0: TabLayout.Tab?) {}
            override fun onTabUnselected(p0: TabLayout.Tab?) {}

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> setTab_1()
                    1 -> setTab_2()
                }
            }
        })

        view.hos_detail_fargment_backImg.setOnClickListener {
            dismiss()
        }
    }

    private fun setTab_1(){

        Log.e(hos_detail_data)
    }

    private fun setTab_2(){

    }


}