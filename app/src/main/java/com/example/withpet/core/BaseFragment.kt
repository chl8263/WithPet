package com.example.withpet.core

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat.getSystemService



abstract class BaseFragment : Fragment() {

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



}