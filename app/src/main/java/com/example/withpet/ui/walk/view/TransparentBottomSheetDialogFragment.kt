package com.example.withpet.ui.walk.view

import android.app.Dialog
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.example.withpet.R
import com.example.withpet.util.Util
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog


// 투명한 BottomSheetDialog
open class TransparentFullSizeBottomSheetDialogFragment : AppBottomSheetDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val rawDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog

        rawDialog.setOnShowListener {
            val dialog = it as BottomSheetDialog

            val nullableBottomSheet = dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout?
            nullableBottomSheet?.let { bottomSheet ->
                bottomSheet.setBackgroundResource(R.color.transparent)
                BottomSheetBehavior.from(bottomSheet).peekHeight = bottomSheet.height
                (bottomSheet.layoutParams as CoordinatorLayout.LayoutParams).behavior = null

                val params = bottomSheet.layoutParams as ViewGroup.MarginLayoutParams
                params.height = ViewGroup.LayoutParams.MATCH_PARENT
                bottomSheet.layoutParams = params
            }

        }
        return rawDialog
    }

}
