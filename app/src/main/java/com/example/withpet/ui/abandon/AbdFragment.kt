package com.example.withpet.ui.abandon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.withpet.R
import com.example.withpet.core.BaseFragment
import com.example.withpet.databinding.AbdFragmentBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class AbdFragment : BaseFragment() {

    lateinit var binding: AbdFragmentBinding
    val viewModel: AbdViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.abd_fragment, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.getSido()
    }

    companion object {
        fun newInstance(): AbdFragment {
            return AbdFragment().apply {
                arguments = Bundle()
            }
        }
    }
}