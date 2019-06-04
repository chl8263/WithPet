package com.example.withpet.ui.hospital

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.withpet.R
import com.example.withpet.core.BaseFragment
import com.example.withpet.databinding.FragmentHospitalBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class HospitalFragment : BaseFragment() {

    lateinit var binding: FragmentHospitalBinding
    val viewModel: HospitalViewModel by viewModel()

    //var fusedLocationProviderClient

    companion object {
        fun newInstance(): HospitalFragment {
            val args = Bundle()
            val fragment = HospitalFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_hospital, container, false)
        binding.viewModel = viewModel
        return binding.root
    }
}