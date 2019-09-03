package com.example.withpet.ui.walk.view

import android.app.Dialog
import android.os.Bundle
import android.view.WindowManager
import com.google.android.material.bottomsheet.BottomSheetDialog


// dim 없는 투명한 BottomSheetDialog
open class TransparentBottomSheetDialogFragment : AppBottomSheetDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val rawDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog

        rawDialog.setOnShowListener {
            val dialog = it as BottomSheetDialog
            dialog.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        }
        return rawDialog
    }

}
