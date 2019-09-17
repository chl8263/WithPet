package com.example.withpet.ui.walk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.withpet.R
import com.example.withpet.databinding.WalkInfoDetailDlgBinding
import com.example.withpet.ui.walk.adapter.WalkDetailAdapter
import com.example.withpet.ui.walk.view.FullSizeAppBottomSheetDialogFragment
import com.example.withpet.vo.walk.WalkBaseDTO
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class WalkInfoDetailDialog : FullSizeAppBottomSheetDialogFragment() {

    lateinit var binding: WalkInfoDetailDlgBinding

    private val vm by sharedViewModel<WalkViewModel>(from = { mActivity })

    private val adapter = WalkDetailAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.walk_info_detail_dlg, container, true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewModel = vm
        binding.list.adapter = adapter

        val rawData = arguments?.getParcelable<WalkBaseDTO?>(WalkInfoDialog.DATA)
        rawData?.let { data ->
            binding.title.text = data._name
            adapter.set(data.extractDetailList())
        }

        vm.dismiss.observe(this, Observer<Boolean> { isDismiss -> if (isDismiss) dismiss() })
    }
}