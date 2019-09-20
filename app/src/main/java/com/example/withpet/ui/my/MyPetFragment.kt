package com.example.withpet.ui.my

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.withpet.R
import com.example.withpet.core.BaseFragment
import com.example.withpet.databinding.MyPetFragmentBinding
import com.example.withpet.ui.walk.WalkFragment
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MyPetFragment : BaseFragment() {

    private val viewModelOwner: MyFragment by lazy { parentFragment as MyFragment }

    lateinit var bb: MyPetFragmentBinding
    private val vm by sharedViewModel<MyViewModel>(from = { viewModelOwner })
    private val myPetVm by viewModel<MyPetViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        bb = DataBindingUtil.inflate(inflater, R.layout.my_pet_fragment, container, false)
        return bb.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bb.myPetVm = myPetVm
        onParseExtra()
    }

    private fun onParseExtra() {
        arguments?.let {
            val index = it.getInt(EXTRA.INDEX, -1)
            if (index < 0) {
                showDialog(message = "잘못된 접근입니다.", positiveButtonText = "확인")
                return@let
            }
            myPetVm.initData(vm.petList[index])
        }
    }


    interface EXTRA {
        companion object {
            const val INDEX = "INDEX"
        }
    }

    companion object {
        fun newInstance(index: Int): MyPetFragment = MyPetFragment().apply {
            this.arguments = Bundle().apply { putInt(EXTRA.INDEX, index) }
        }
    }
}