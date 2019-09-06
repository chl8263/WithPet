package com.example.withpet.ui.walk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.withpet.R
import com.example.withpet.databinding.WalkInfoDetailDlgBinding
import com.example.withpet.databinding.WalkInfoDlgBinding
import com.example.withpet.ui.walk.view.FullSizeAppBottomSheetDialogFragment
import com.example.withpet.ui.walk.view.TransparentBottomSheetDialogFragment
import com.example.withpet.util.Log
import com.example.withpet.vo.WalkBicycleDTO

class WalkInfoDetailDialog : FullSizeAppBottomSheetDialogFragment() {

    lateinit var binding: WalkInfoDetailDlgBinding

    private val data by lazy { arguments?.getParcelable(WalkInfoDialog.DATA) as WalkBicycleDTO? }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.walk_info_detail_dlg, container, true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.w(data.toString())
    }
}