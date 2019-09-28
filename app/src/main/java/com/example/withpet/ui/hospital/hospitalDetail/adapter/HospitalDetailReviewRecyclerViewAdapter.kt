package com.example.withpet.ui.hospital.hospitalDetail.adapter

import android.icu.text.SimpleDateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.withpet.R
import com.example.withpet.vo.hospital.HospitalReviewDTO
import kotlinx.android.synthetic.main.hospital_review_item.view.*
import java.util.*
import kotlin.collections.ArrayList
import com.example.withpet.util.Log
import java.text.ParseException


class HospitalDetailReviewRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var reviewList: ArrayList<HospitalReviewDTO> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view = LayoutInflater.from(parent.context)
            .inflate(com.example.withpet.R.layout.hospital_review_item, parent, false)

        return CustomViewHolder(view)
    }

    override fun getItemCount(): Int = reviewList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        // TODO:   사용자 이미지 넣기
        //holder.itemView.hospital_review_item_imageView.setImageResource(R.drawable.ic_history)

        holder.itemView.hospital_review_item_userId.text = reviewList[position].userId

        when (reviewList[position].starPoint) {
            1 -> {
                holder.itemView.hos_review_item_star_img_1.setImageResource(com.example.withpet.R.drawable.ic_star)
                holder.itemView.hos_review_item_star_img_2.setImageResource(com.example.withpet.R.drawable.ic_empty_star)
                holder.itemView.hos_review_item_star_img_3.setImageResource(com.example.withpet.R.drawable.ic_empty_star)
                holder.itemView.hos_review_item_star_img_4.setImageResource(com.example.withpet.R.drawable.ic_empty_star)
                holder.itemView.hos_review_item_star_img_5.setImageResource(com.example.withpet.R.drawable.ic_empty_star)
            }
            2 -> {
                holder.itemView.hos_review_item_star_img_1.setImageResource(com.example.withpet.R.drawable.ic_star)
                holder.itemView.hos_review_item_star_img_2.setImageResource(com.example.withpet.R.drawable.ic_star)
                holder.itemView.hos_review_item_star_img_3.setImageResource(com.example.withpet.R.drawable.ic_empty_star)
                holder.itemView.hos_review_item_star_img_4.setImageResource(com.example.withpet.R.drawable.ic_empty_star)
                holder.itemView.hos_review_item_star_img_5.setImageResource(com.example.withpet.R.drawable.ic_empty_star)
            }
            3 -> {
                holder.itemView.hos_review_item_star_img_1.setImageResource(com.example.withpet.R.drawable.ic_star)
                holder.itemView.hos_review_item_star_img_2.setImageResource(com.example.withpet.R.drawable.ic_star)
                holder.itemView.hos_review_item_star_img_3.setImageResource(com.example.withpet.R.drawable.ic_star)
                holder.itemView.hos_review_item_star_img_4.setImageResource(com.example.withpet.R.drawable.ic_empty_star)
                holder.itemView.hos_review_item_star_img_5.setImageResource(com.example.withpet.R.drawable.ic_empty_star)
            }
            4 -> {
                holder.itemView.hos_review_item_star_img_1.setImageResource(com.example.withpet.R.drawable.ic_star)
                holder.itemView.hos_review_item_star_img_2.setImageResource(com.example.withpet.R.drawable.ic_star)
                holder.itemView.hos_review_item_star_img_3.setImageResource(com.example.withpet.R.drawable.ic_star)
                holder.itemView.hos_review_item_star_img_4.setImageResource(com.example.withpet.R.drawable.ic_star)
                holder.itemView.hos_review_item_star_img_5.setImageResource(com.example.withpet.R.drawable.ic_empty_star)
            }
            5 -> {
                holder.itemView.hos_review_item_star_img_1.setImageResource(com.example.withpet.R.drawable.ic_star)
                holder.itemView.hos_review_item_star_img_2.setImageResource(com.example.withpet.R.drawable.ic_star)
                holder.itemView.hos_review_item_star_img_3.setImageResource(com.example.withpet.R.drawable.ic_star)
                holder.itemView.hos_review_item_star_img_4.setImageResource(com.example.withpet.R.drawable.ic_star)
                holder.itemView.hos_review_item_star_img_5.setImageResource(com.example.withpet.R.drawable.ic_star)
            }
        }

        // TODO:  날짜 비교 처리
        var betweenDate = calDataBetween(reviewList[position].timeStamp.split("_")[0]) ?: "unknown"
        holder.itemView.hos_review_item_dayAgo.text = betweenDate
        holder.itemView.hospital_review_item_comment.text = reviewList[position].comment
    }

    private inner class CustomViewHolder(var view: View) : RecyclerView.ViewHolder(view)

    private fun calDataBetween(targetDay: String): String? {

        try {
            var today = java.text.SimpleDateFormat("yyyy-MM-dd_HHmmss").format(Date())
            val format = SimpleDateFormat("yyyy-mm-dd")

            val FirstDate = format.parse(today)
            val SecondDate = format.parse(targetDay)

            val calDate = FirstDate.time - SecondDate.time

            var calDateDays = calDate / (24 * 60 * 60 * 1000)

            var result = Math.abs(calDateDays).toInt()

            when (result){
                0 -> return "오늘"
                else -> return "$result 일전"
            }
        } catch (e: ParseException) {
            return null
        }
    }
}