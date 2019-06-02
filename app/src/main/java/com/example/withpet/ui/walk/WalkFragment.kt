package com.example.withpet.ui.walk

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.withpet.R
import com.example.withpet.core.BaseFragment
import com.example.withpet.databinding.FragmentWalkBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class WalkFragment : BaseFragment() {

    lateinit var binding: FragmentWalkBinding
    val viewModel: WalkViewModel by viewModel()

    companion object {
        fun newInstance(): WalkFragment {
            val args = Bundle()
            val fragment = WalkFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_walk, container, false)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.goMapActivity.observe(this, Observer {
            mContext?.let { mContext -> startActivity(Intent(mContext, MapsTestActivity::class.java)) }
        })
    }
}