package com.example.withpet.ui.abandon.adapter

import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

object AbdBindingAdapter {
    val TOP = -1
    val BOTTOM = 1

    @JvmStatic
    @BindingAdapter("app:scrollBottom")
    fun scrollBottom(recyclerView: RecyclerView, function: () -> Unit) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val itemCount = recyclerView.adapter?.itemCount ?: 0
                if (itemCount > 0 && !recyclerView.canScrollVertically(BOTTOM)) {
                    function.invoke()
                }
            }
        })
    }

    @JvmStatic
    @BindingAdapter("app:call")
    fun call(v: View, tel: String) {
        v.setOnClickListener { v.context.startActivity(Intent(Intent.ACTION_DIAL, Uri.parse("tel:$tel"))) }
    }
}