package com.example.withpet.ui.walk.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.withpet.core.adapter.SingleAdapter
import com.example.withpet.databinding.WalkSearchItemBinding
import com.example.withpet.ui.walk.Location
import com.example.withpet.util.DistanceUtil
import com.example.withpet.vo.walk.WalkBaseDTO


class WalkSearchAdapter(private val onItemClickListener: (WalkBaseDTO) -> Unit) : SingleAdapter<WalkSearchAdapter.WalkSearchHolder, WalkBaseDTO>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WalkSearchHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val binding = WalkSearchItemBinding.inflate(inflater, parent, false)
        return WalkSearchHolder(binding)
    }

    override fun onBind(holder: WalkSearchHolder, item: WalkBaseDTO) = holder.bind(item)

    inner class WalkSearchHolder(private val binding: WalkSearchItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: WalkBaseDTO) {
            binding.run {
                data = item
                distanceValue = DistanceUtil.getDistance(Location.currentLocation, item.location)
                root.setOnClickListener { onItemClickListener.invoke(item) }
            }
        }
    }

}