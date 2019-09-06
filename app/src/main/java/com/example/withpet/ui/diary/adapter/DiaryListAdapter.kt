package com.example.withpet.ui.diary.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.withpet.core.adapter.SingleAdapter
import com.example.withpet.databinding.DiaryListRowBinding
import com.example.withpet.vo.diary.DiaryDTO


class DiaryListAdapter : SingleAdapter<DiaryListHolder, DiaryDTO>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiaryListHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val binding = DiaryListRowBinding.inflate(inflater, parent, false)
        return DiaryListHolder(binding)
    }

    override fun onBind(holder: DiaryListHolder, item: DiaryDTO) = holder.bind(item)

}


class DiaryListHolder(private val binding: DiaryListRowBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: DiaryDTO) {
        with(binding) {
            executePendingBindings()
        }
    }
}
