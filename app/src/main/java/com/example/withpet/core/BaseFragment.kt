package com.example.withpet.core

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


abstract class BaseFragment : Fragment() {

    var mLastClickTime: Long = 0

    var mContext: Context? = null
    lateinit var mActivity: BaseActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = activity as BaseActivity
        mContext = context
    }

    // Dialog
    fun getDialog(
            title: Any? = null,
            message: Any? = null,
            view: View? = null,
            positiveButtonText: Any? = null,
            positiveListener: ((dialogInterface: DialogInterface, position: Int) -> Unit)? = null,
            negativeButtonText: Any? = null,
            negativeListener: ((dialogInterface: DialogInterface, position: Int) -> Unit)? = null,
            neutralButtonText: Any? = null,
            neutralListener: ((dialogInterface: DialogInterface, position: Int) -> Unit)? = null): AlertDialog? {
        return mActivity.getDialog(title, message, view, positiveButtonText, positiveListener, negativeButtonText, negativeListener, neutralButtonText, neutralListener)
    }

    fun showDialog(title: Any? = null,
                   message: Any? = null,
                   view: View? = null,
                   positiveButtonText: Any = "확인",
                   positiveListener: ((dialogInterface: DialogInterface, position: Int) -> Unit)? = null,
                   negativeButtonText: Any? = null,
                   negativeListener: ((dialogInterface: DialogInterface, position: Int) -> Unit)? = null,
                   neutralButtonText: Any? = null,
                   neutralListener: ((dialogInterface: DialogInterface, position: Int) -> Unit)? = null) {
        mActivity.showDialog(title, message, view, positiveButtonText, positiveListener, negativeButtonText, negativeListener, neutralButtonText, neutralListener)
    }

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