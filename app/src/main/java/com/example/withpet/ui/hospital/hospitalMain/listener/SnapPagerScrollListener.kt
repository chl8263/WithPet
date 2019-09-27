package com.example.withpet.ui.hospital.hospitalMain.listener

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.annotation.IntDef



class SnapPagerScrollListener(snapHelper: PagerSnapHelper , type : Type , notifyOnInit : Boolean , listener:OnChangeListener ) : RecyclerView.OnScrollListener(){

    enum class Type {
        ON_SCROLL , ON_SETTLED
    }

    interface OnChangeListener {
        fun onSnapped(position: Int)
    }

    // Properties
    private var snapHelper: PagerSnapHelper? = null
    private var type  = Type.ON_SCROLL
    private var notifyOnInit: Boolean = false
    private var listener: OnChangeListener? = null
    private var snapPosition: Int = 0

    init {
        this.snapHelper = snapHelper
        this.type = type
        this.notifyOnInit = notifyOnInit
        this.listener = listener
        this.snapPosition = RecyclerView.NO_POSITION
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)
        if ((type == Type.ON_SCROLL) || !hasItemPosition()) {
            notifyListenerIfNeeded(getSnapPosition(recyclerView))
        }
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
        if (type == Type.ON_SETTLED && newState == RecyclerView.SCROLL_STATE_IDLE) {
            notifyListenerIfNeeded(getSnapPosition(recyclerView))
        }
    }

    private fun getSnapPosition(recyclerView: RecyclerView): Int {
        val layoutManager = recyclerView.layoutManager ?: return RecyclerView.NO_POSITION

        val snapView = snapHelper?.findSnapView(layoutManager) ?: return RecyclerView.NO_POSITION

        return layoutManager.getPosition(snapView)
    }

    private fun notifyListenerIfNeeded(newSnapPosition: Int) {
        if (snapPosition !== newSnapPosition) {
            if (notifyOnInit && !hasItemPosition()) {
                listener?.onSnapped(newSnapPosition)
            } else if (hasItemPosition()) {
                listener?.onSnapped(newSnapPosition)
            }
            snapPosition = newSnapPosition
        }
    }

    private fun hasItemPosition(): Boolean {
        return snapPosition !== RecyclerView.NO_POSITION
    }
}