package com.example.withpet.ui.abandon.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.withpet.core.adapter.SingleAdapter
import com.example.withpet.databinding.AbdSelectSigunguItemBinding
import com.example.withpet.ui.abandon.enums.SigunguEnum


class AbdSigunguAdapter(private val select: (enum: SigunguEnum) -> Unit) : SingleAdapter<AbdSigunguHolder, SigunguEnum>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbdSigunguHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val binding = AbdSelectSigunguItemBinding.inflate(inflater, parent, false)
        return AbdSigunguHolder(binding)
    }

    override fun onBind(holder: AbdSigunguHolder, item: SigunguEnum) = holder.bind(item, select)
}

class AbdSigunguHolder(private val binding: AbdSelectSigunguItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(data: SigunguEnum, select: (enum: SigunguEnum) -> Unit) {
        binding.run {
            this.data = data
            binding.root.setOnClickListener {
                select.invoke(data)
            }
        }
    }
}
