package com.example.withpet.ui.hospitalDetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.withpet.core.BaseFragment
import com.example.withpet.databinding.FragmentHosDetailBinding
import com.example.withpet.databinding.FragmentHospitalBinding
import com.example.withpet.ui.hospital.HospitalFragment
import com.example.withpet.ui.hospital.HospitalViewModel
import com.example.withpet.ui.hospital.adapter.HospitalHistorySearchRecyclerViewAdapter
import com.example.withpet.ui.hospital.adapter.HospitalSearchRecyclerViewAdapter
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.hospital_detail_fragment.view.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class HosDetailFragment : BaseFragment() {

    //private val hospitalAdapter: HospitalSearchRecyclerViewAdapter by inject()
    //private val historyAdapter: HospitalHistorySearchRecyclerViewAdapter by inject()

    lateinit var binding: FragmentHosDetailBinding
    val viewModel: HosDetailViewModel by viewModel()

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
        view.hos_detail_fragment_tab.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
            override fun onTabReselected(p0: TabLayout.Tab?) {}
            override fun onTabUnselected(p0: TabLayout.Tab?) {}

            override fun onTabSelected(tab: TabLayout.Tab?) {
                when (tab?.position) {
                    0 -> return
                    1 -> return
                }
            }
        })
    }

}