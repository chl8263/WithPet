package com.example.withpet.ui.my.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.withpet.core.adapter.SingleAdapter
import com.example.withpet.databinding.MyPetDiaryRowBinding
import com.example.withpet.vo.diary.DiaryDTO

class MyPetDiaryAdapter : SingleAdapter<MyPetDiaryHolder, DiaryDTO>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyPetDiaryHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val binding = MyPetDiaryRowBinding.inflate(inflater, parent, false)
        return MyPetDiaryHolder(binding)
    }

    override fun onBind(holder: MyPetDiaryHolder, item: DiaryDTO) = holder.bind(item)
}

class MyPetDiaryHolder(private val binding: MyPetDiaryRowBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: DiaryDTO) {
        with(binding) {

            if (item.dummyHeader != 0) {
                image.scaleType = ImageView.ScaleType.CENTER_INSIDE
                image.setImageResource(item.dummyHeader)
            } else {
                Glide.with(image)
                        .load(item.imageUrl)
                        .centerCrop()
                        .into(image)
            }
        }
    }
}