package com.example.withpet.ui.walk

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import com.example.withpet.R
import com.example.withpet.ui.walk.view.AppBottomSheetDialogFragment
import com.example.withpet.databinding.WalkInfoDlgBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class WalkInfoDlg : AppBottomSheetDialogFragment() {

    lateinit var binding: WalkInfoDlgBinding

    private val title by lazy { arguments?.getString(ROAD_NAME) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.walk_info_dlg, container, true)
        binding.title = title
        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val rawDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog

        rawDialog.setOnShowListener {
            val dialog = it as BottomSheetDialog
            // dim 삭제
//            dialog.window?.setDimAmount(0f)
            dialog.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        }
        return rawDialog
    }

    companion object {
        const val ROAD_NAME = "ROAD_NAME"
    }
}