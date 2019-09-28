package com.example.withpet.ui.walk.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.withpet.core.adapter.SingleAdapter
import com.example.withpet.databinding.WalkInfoDetailItemBinding
import com.example.withpet.vo.walk.WalkDetailRowDTO


class WalkDetailAdapter : SingleAdapter<WalkDetailHolder, WalkDetailRowDTO>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WalkDetailHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val binding = WalkInfoDetailItemBinding.inflate(inflater, parent, false)
        return WalkDetailHolder(binding)
    }

    override fun onBind(holder: WalkDetailHolder, item: WalkDetailRowDTO) = holder.bind(item)
}

class WalkDetailHolder(private val binding: WalkInfoDetailItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: WalkDetailRowDTO) {
        binding.run {
            data = item
        }
    }
}
