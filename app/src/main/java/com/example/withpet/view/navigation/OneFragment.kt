package com.example.withpet.view.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.withpet.R
import com.example.withpet.core.BaseFragment
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class OneFragment : BaseFragment() {

    //lateinit var binding : BindingName
    //lateinit var vieModel : Viewmodel by viewModel()

    companion object {
        fun newInstance(): OneFragment {
            val args = Bundle()
            val fragment = OneFragment()
            fragment.arguments = args
            return fragment
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //binding = DataBindingUtil.inflate(inflater, R.layout.activity_main, container, false)
        //binding.viewModel = viewModel
        //val view = binding.root
        return view
    }
}