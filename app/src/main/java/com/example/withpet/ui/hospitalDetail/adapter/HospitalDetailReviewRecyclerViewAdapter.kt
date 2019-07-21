package com.example.withpet.ui.hospitalDetail.adapter

import android.os.Build.VERSION_CODES.P
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.withpet.R
import com.example.withpet.ui.hospital.usecase.HistoryRepository
import com.example.withpet.util.Const
import com.example.withpet.vo.hospital.HospitalSearchDTO
import com.example.withpet.vo.eventBus.HospitalCardEventVo
import com.example.withpet.vo.hospital.HospitalReviewDTO
import kotlinx.android.synthetic.main.hospital_review_item.view.*
import kotlinx.android.synthetic.main.hospital_search_item.view.*
import org.greenrobot.eventbus.EventBus

class HospitalDetailReviewRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var reviewList : ArrayList<HospitalReviewDTO> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.hospital_review_item,parent,false)

        return CustomViewHolder(view)
    }

    override fun getItemCount(): Int = reviewList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        // TODO:   사용자 이미지 넣기
        //holder.itemView.hospital_review_item_imageView.setImageResource(R.drawable.ic_history)

        holder.itemView.hospital_review_item_userId.text = reviewList[position].userId

        when (reviewList[position].starPoint){
            1 -> {
                holder.itemView.hos_review_item_star_img_1.setImageResource(R.drawable.ic_star)
                holder.itemView.hos_review_item_star_img_2.setImageResource(R.drawable.ic_empty_star)
                holder.itemView.hos_review_item_star_img_3.setImageResource(R.drawable.ic_empty_star)
                holder.itemView.hos_review_item_star_img_4.setImageResource(R.drawable.ic_empty_star)
                holder.itemView.hos_review_item_star_img_5.setImageResource(R.drawable.ic_empty_star)
            }
            2 -> {
                holder.itemView.hos_review_item_star_img_1.setImageResource(R.drawable.ic_star)
                holder.itemView.hos_review_item_star_img_2.setImageResource(R.drawable.ic_star)
                holder.itemView.hos_review_item_star_img_3.setImageResource(R.drawable.ic_empty_star)
                holder.itemView.hos_review_item_star_img_4.setImageResource(R.drawable.ic_empty_star)
                holder.itemView.hos_review_item_star_img_5.setImageResource(R.drawable.ic_empty_star)
            }
            3 -> {
                holder.itemView.hos_review_item_star_img_1.setImageResource(R.drawable.ic_star)
                holder.itemView.hos_review_item_star_img_2.setImageResource(R.drawable.ic_star)
                holder.itemView.hos_review_item_star_img_3.setImageResource(R.drawable.ic_star)
                holder.itemView.hos_review_item_star_img_4.setImageResource(R.drawable.ic_empty_star)
                holder.itemView.hos_review_item_star_img_5.setImageResource(R.drawable.ic_empty_star)
            }
            4 -> {
                holder.itemView.hos_review_item_star_img_1.setImageResource(R.drawable.ic_star)
                holder.itemView.hos_review_item_star_img_2.setImageResource(R.drawable.ic_star)
                holder.itemView.hos_review_item_star_img_3.setImageResource(R.drawable.ic_star)
                holder.itemView.hos_review_item_star_img_4.setImageResource(R.drawable.ic_star)
                holder.itemView.hos_review_item_star_img_5.setImageResource(R.drawable.ic_empty_star)
            }
            5 -> {
                holder.itemView.hos_review_item_star_img_1.setImageResource(R.drawable.ic_star)
                holder.itemView.hos_review_item_star_img_2.setImageResource(R.drawable.ic_star)
                holder.itemView.hos_review_item_star_img_3.setImageResource(R.drawable.ic_star)
                holder.itemView.hos_review_item_star_img_4.setImageResource(R.drawable.ic_star)
                holder.itemView.hos_review_item_star_img_5.setImageResource(R.drawable.ic_star)
            }
        }

        // TODO:  날짜 비교 처리
        holder.itemView.hos_review_item_dayAgo.text = reviewList[position].userId
        holder.itemView.hospital_review_item_comment.text = reviewList[position].comment

    }

    private inner class CustomViewHolder (var view : View) : RecyclerView.ViewHolder(view)
}