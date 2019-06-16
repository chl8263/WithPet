package com.example.withpet.ui.walk

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.withpet.R
import com.example.withpet.ui.walk.view.AppBottomSheetDialogFragment
import com.example.withpet.databinding.WalkInfoDlgBinding

class WalkInfoDlg : AppBottomSheetDialogFragment() {

    lateinit var binding: WalkInfoDlgBinding

    val title by lazy { arguments?.getString(ROAD_NAME) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.walk_info_dlg, container, true)
        binding.title = title
            return binding.root
    }

    companion object {
        const val ROAD_NAME = "ROAD_NAME"
    }
}