package com.example.withpet.ui.walk.view

import android.app.Dialog
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

open class FullSizeAppBottomSheetDialogFragment : AppBottomSheetDialogFragment() {

    private val topMargin by lazy { arguments?.getFloat(TOP_MARGIN) }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val rawDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog

        rawDialog.setOnShowListener {
            val dialog = it as BottomSheetDialog

            val nullableBottomSheet = dialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout?
            nullableBottomSheet?.let { bottomSheet ->
                BottomSheetBehavior.from(bottomSheet).peekHeight = bottomSheet.height
                (bottomSheet.layoutParams as CoordinatorLayout.LayoutParams).behavior = null

                val params = bottomSheet.layoutParams as ViewGroup.MarginLayoutParams
                params.height = ViewGroup.LayoutParams.MATCH_PARENT
                topMargin?.let { margin -> params.topMargin = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, margin, context?.resources?.displayMetrics).toInt() }
                bottomSheet.layoutParams = params
            }

        }
        return rawDialog
    }
}
