package com.example.withpet.ui.walk.view

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Lifecycle
import com.example.withpet.R
import com.example.withpet.core.BaseActivity
import com.example.withpet.util.Log
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

open class AppBottomSheetDialogFragment : BottomSheetDialogFragment() {

    protected lateinit var mContext: Context
    protected lateinit var mActivity: BaseActivity
    private var mWindow: Window? = null
    protected lateinit var mDecor: View
    protected lateinit var mResources: Resources

    fun getBaseActivity(): BaseActivity? {
        return super.getActivity() as BaseActivity?
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mDecor = view

        parseExtra()
        loadOnce()
        reload()
        updateUI()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activity = getBaseActivity() ?: throw RuntimeException("BFragment must in the BActivity")
        mActivity = activity
        mContext = mActivity
        mResources = mContext.resources
        mWindow = activity.window

        setStyle(DialogFragment.STYLE_NORMAL, R.style.BottomSheet)
    }

    protected fun parseExtra() {
        try {
            onParseExtra()
        } catch (e: Exception) {
            Log.printStackTrace(e)
        }
    }

    protected fun loadOnce() {
        onLoadOnce()
    }

    protected fun reload() {
        onReload()
    }

    protected fun clear() {
        try {
            onClear()
        } catch (e: Exception) {
            Log.printStackTrace(e)
        }

    }

    protected fun load() {
        if (lifecycle.currentState == Lifecycle.State.DESTROYED) {
            Log.w("activity is Lifecycle destroyed")
            return
        }
        try {
            onLoad()
        } catch (e: Exception) {
            Log.printStackTrace(e)
        }

    }

    protected fun updateUI() {
        try {
            onUpdateUI()
        } catch (e: Exception) {
            Log.printStackTrace(e)
        }

    }

    fun showProgress() {
        mActivity.showProgress()
    }

    fun dismissProgress() {
        mActivity.dismissProgress()
    }


    protected open fun onParseExtra() {
        //        Log.d(getClass(), getIntent());
    }

    protected open fun onLoadOnce() {
        //        Log.d(getClass(), "onLoadOnce");
    }

    protected open fun onReload() {
        //        Log.d(getClass(), "onReload");
        clear()
        load()
    }

    protected open fun onClear() {
        //        Log.d(getClass(), "onClear");
    }

    protected open fun onLoad() {
        //        Log.d(getClass(), "onLoad");
    }

    protected open fun onUpdateUI() {
        //        Log.d(getClass(), "onUpdateUI");
    }

    companion object{
        const val TOP_MARGIN = "TOP_MARGIN"
    }
}
