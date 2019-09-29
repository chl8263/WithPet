package com.example.withpet.core

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import com.example.withpet.util.Log
import com.example.withpet.util.getText
import android.widget.EditText
import android.view.Window.FEATURE_NO_TITLE
import android.widget.ProgressBar
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.transition.TransitionInflater


abstract class BaseActivity : AppCompatActivity() {

    lateinit var mContext: Context
    lateinit var mActivity: BaseActivity
    lateinit var mDecor: View

    lateinit var mProgress: Dialog
    protected open fun setRequestedOrientation() {
        try {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT
        } catch (ignore: IllegalStateException) {
        }
    }

    protected open fun setSoftInputMode() {
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN or WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        setRequestedOrientation()
        setSoftInputMode()
        super.onCreate(savedInstanceState)

        mContext = this
        mActivity = this
        mDecor = window.decorView

        mProgress = createProgress()
    }

    // EditText 외부클릭시 focus 제거
    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus()
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
                    imm?.hideSoftInputFromWindow(v.windowToken, 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
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

    open fun createProgress(): Dialog {
        val dialog = Dialog(mContext)
        dialog.requestWindowFeature(FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.window?.apply {
            setContentView(ProgressBar(mContext, null, android.R.attr.progressBarStyleLarge))
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        return dialog
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

    fun onHeaderBack(v: View) {
        Log.d(javaClass, "onHeaderBack")
        onBackPressed()
    }


    /**
     * replace frahment
     */
    fun startFragmentDialog(dialogFragment: DialogFragment, transitionId: Int) {
        val fragmentTag = dialogFragment.javaClass.simpleName

        val fragmentManager = supportFragmentManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialogFragment.enterTransition = TransitionInflater.from(this).inflateTransition(transitionId)
        }
        fragmentManager.popBackStack(fragmentTag, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        val transaction = fragmentManager.beginTransaction()
        transaction.setReorderingAllowed(true)
            .addToBackStack(fragmentTag)
            .replace(android.R.id.content, dialogFragment)
            .commit()
    }
}