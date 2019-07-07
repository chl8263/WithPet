package com.example.withpet

import android.content.Context
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.transition.TransitionInflater


abstract class BaseDialogFragment : DialogFragment() {

    var mLastClickTime: Long = 0

    fun clickTimeCheck(): Boolean {
        if (System.currentTimeMillis() - mLastClickTime < 700) {
            return true
        }
        mLastClickTime = System.currentTimeMillis()
        return false
    }

    /**
     * replace frahment
     */
    fun startFragmentDialog(dialogFragment: DialogFragment, transitionId: Int) {
        if (clickTimeCheck()) {
            return
        }
        addFragmentDialog(dialogFragment,transitionId)
    }

    // fragment dioalog
    fun addFragmentDialog(dialogFragment: DialogFragment, transitionId: Int) {
        val fragmentTag = dialogFragment.javaClass.simpleName
        val fragmentManager = fragmentManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialogFragment.setEnterTransition(TransitionInflater.from(context).inflateTransition(transitionId))
        }
        fragmentManager!!.popBackStack(fragmentTag, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        val transaction = fragmentManager!!.beginTransaction()
        transaction.setReorderingAllowed(true)
            .addToBackStack(fragmentTag)
            .add(android.R.id.content, dialogFragment)
            .commit()
    }


}