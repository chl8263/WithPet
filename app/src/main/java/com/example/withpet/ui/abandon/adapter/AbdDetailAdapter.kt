package com.example.withpet.ui.abandon.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.withpet.core.adapter.SingleAdapter
import com.example.withpet.databinding.WalkInfoDetailItemBinding
import com.example.withpet.vo.walk.WalkDetailRowDTO


class AbdDetailAdapter : SingleAdapter<AbdDetailHolder, WalkDetailRowDTO>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbdDetailHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val binding = WalkInfoDetailItemBinding.inflate(inflater, parent, false)
        return AbdDetailHolder(binding)
    }

    override fun onBind(holder: AbdDetailHolder, item: WalkDetailRowDTO) = holder.bind(item)
}

class AbdDetailHolder(private val binding: WalkInfoDetailItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: WalkDetailRowDTO) {
        binding.run {
            data = item
        }
    }
}
