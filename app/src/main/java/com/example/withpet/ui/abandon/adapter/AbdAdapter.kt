package com.example.withpet.ui.abandon.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.withpet.core.adapter.SingleAdapter
import com.example.withpet.databinding.AbdItemBinding
import com.example.withpet.vo.abandon.AbandonAnimalDTO


class AbdAdapter : SingleAdapter<AbdHolder, AbandonAnimalDTO>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbdHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val binding = AbdItemBinding.inflate(inflater, parent, false)
        return AbdHolder(binding)
    }

    override fun onBind(holder: AbdHolder, item: AbandonAnimalDTO) = holder.bind(item)
}

class AbdHolder(private val binding: AbdItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: AbandonAnimalDTO) {
        binding.run {
            data = item
        }
    }
}
