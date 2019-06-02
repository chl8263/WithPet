package com.example.withpet.core

import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDialog
import androidx.lifecycle.Lifecycle
import com.example.withpet.util.Log
import com.example.withpet.util.getText

abstract class BaseActivity : AppCompatActivity() {

    lateinit var mContext: Context
    lateinit var mActivity: BaseActivity
    lateinit var mDecor: View

    lateinit var mProgress: AppCompatDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mContext = this
        mActivity = this
        mDecor = window.decorView

        mProgress = createProgress()
    }


    // Dialog
    fun getDialog(title: Any? = null,
                  message: Any? = null,
                  view: View? = null,
                  positiveButtonText: Any? = null,
                  positiveListener: ((dialogInterface: DialogInterface, position: Int) -> Unit)? = null,
                  negativeButtonText: Any? = null,
                  negativeListener: ((dialogInterface: DialogInterface, position: Int) -> Unit)? = null,
                  neutralButtonText: Any? = null,
                  neutralListener: ((dialogInterface: DialogInterface, position: Int) -> Unit)? = null): AlertDialog? {
        return AlertDialog.Builder(this).apply {
            if (title != null) setTitle(getText(title))
            if (message != null) setMessage(getText(message))
            if (view != null) setView(view)
            if (positiveButtonText != null) setPositiveButton(getText(positiveButtonText), positiveListener)
            if (negativeButtonText != null) setNegativeButton(getText(negativeButtonText), negativeListener)
            if (neutralButtonText != null) setNeutralButton(getText(neutralButtonText), neutralListener)
        }.create()
    }


    fun showDialog(title: Any? = null,
                   message: Any? = null,
                   view: View? = null,
                   positiveButtonText: Any? = null,
                   positiveListener: ((dialogInterface: DialogInterface, position: Int) -> Unit)? = null,
                   negativeButtonText: Any? = null,
                   negativeListener: ((dialogInterface: DialogInterface, position: Int) -> Unit)? = null,
                   neutralButtonText: Any? = null,
                   neutralListener: ((dialogInterface: DialogInterface, position: Int) -> Unit)? = null) {
        val dialog = getDialog(title, message, view, positiveButtonText, positiveListener, negativeButtonText, negativeListener, neutralButtonText, neutralListener)
        if (lifecycle.currentState == Lifecycle.State.DESTROYED) {
            Log.e("getDialog Lifecycle is Destroyed")
            return
        }
        if (isFinishing) {
            Log.e("getDialog Activity is finish")
            return
        }
        dialog?.show()
    }

    open fun createProgress(): AppCompatDialog {
        val context = mContext
        val builder = AlertDialog.Builder(context)
        builder.setView(ProgressBar(context, null, android.R.attr.progressBarStyleLarge))
        return builder.create().apply {
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    fun showProgress() {
        if (lifecycle.currentState == Lifecycle.State.DESTROYED) {
            Log.e("showProgress Lifecycle is Destroyed")
            return
        }
        if (isFinishing) {
            Log.e("showProgress Activity is finish")
            return
        }

        if (mProgress.isShowing) {
            Log.e("showProgress is showing")
            return
        }

        mProgress.show()
    }

    fun dismissProgress() {
        if (mProgress.isShowing) {
            Log.i("tes : dismissProgress")
            mProgress.dismiss()
        }

    }
}