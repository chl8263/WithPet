package com.example.withpet.ui.hospital.hospitalMain.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.withpet.R
import com.example.withpet.ui.hospital.hospitalDetail.HosDetailFragment
import com.example.withpet.ui.hospital.hospitalMain.listener.OnShowHospitalDialogListener
import com.example.withpet.util.Const
import com.example.withpet.vo.hospital.HospitalSearchDTO
import kotlinx.android.synthetic.main.fragment_hospital.*
import kotlinx.android.synthetic.main.hospital_cardview_item.view.*
import kotlinx.android.synthetic.main.hospital_search_item.view.*

class HospitalCardViewRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var searchList : ArrayList<HospitalSearchDTO> = arrayListOf()

    var mShowHospitalDialogListener : OnShowHospitalDialogListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.hospital_cardview_item,parent,false)

        return CustomViewHolder(view)
    }

    override fun getItemCount(): Int = searchList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        holder.itemView.hos_cardView_item_layout.setOnClickListener {
            mShowHospitalDialogListener?.let { mShowHospitalDialogListener?.showHospitalDetailDialog(searchList[position]) }
        }

        holder.itemView.cardview_hos_card_Title.text = searchList[position].name.toString()
        holder.itemView.cardview_hos_card_address.text = searchList[position].address

        when (searchList[position].starAvg.toInt()){
            1 -> {
                holder.itemView.cardview_hos_card_star_img_1.setImageResource(R.drawable.ic_star)
                holder.itemView.cardview_hos_card_star_img_2.setImageResource(R.drawable.ic_empty_star)
                holder.itemView.cardview_hos_card_star_img_3.setImageResource(R.drawable.ic_empty_star)
                holder.itemView.cardview_hos_card_star_img_4.setImageResource(R.drawable.ic_empty_star)
                holder.itemView.cardview_hos_card_star_img_5.setImageResource(R.drawable.ic_empty_star)
            }
            2 -> {
                holder.itemView.cardview_hos_card_star_img_1.setImageResource(R.drawable.ic_star)
                holder.itemView.cardview_hos_card_star_img_2.setImageResource(R.drawable.ic_star)
                holder.itemView.cardview_hos_card_star_img_3.setImageResource(R.drawable.ic_empty_star)
                holder.itemView.cardview_hos_card_star_img_4.setImageResource(R.drawable.ic_empty_star)
                holder.itemView.cardview_hos_card_star_img_5.setImageResource(R.drawable.ic_empty_star)
            }
            3 -> {
                holder.itemView.cardview_hos_card_star_img_1.setImageResource(R.drawable.ic_star)
                holder.itemView.cardview_hos_card_star_img_2.setImageResource(R.drawable.ic_star)
                holder.itemView.cardview_hos_card_star_img_3.setImageResource(R.drawable.ic_star)
                holder.itemView.cardview_hos_card_star_img_4.setImageResource(R.drawable.ic_empty_star)
                holder.itemView.cardview_hos_card_star_img_5.setImageResource(R.drawable.ic_empty_star)
            }
            4 -> {
                holder.itemView.cardview_hos_card_star_img_1.setImageResource(R.drawable.ic_star)
                holder.itemView.cardview_hos_card_star_img_2.setImageResource(R.drawable.ic_star)
                holder.itemView.cardview_hos_card_star_img_3.setImageResource(R.drawable.ic_star)
                holder.itemView.cardview_hos_card_star_img_4.setImageResource(R.drawable.ic_star)
                holder.itemView.cardview_hos_card_star_img_5.setImageResource(R.drawable.ic_empty_star)
            }
            5 -> {
                holder.itemView.cardview_hos_card_star_img_1.setImageResource(R.drawable.ic_star)
                holder.itemView.cardview_hos_card_star_img_2.setImageResource(R.drawable.ic_star)
                holder.itemView.cardview_hos_card_star_img_3.setImageResource(R.drawable.ic_star)
                holder.itemView.cardview_hos_card_star_img_4.setImageResource(R.drawable.ic_star)
                holder.itemView.cardview_hos_card_star_img_5.setImageResource(R.drawable.ic_star)
            }
            else -> {
                holder.itemView.cardview_hos_card_star_img_1.setImageResource(R.drawable.ic_empty_star)
                holder.itemView.cardview_hos_card_star_img_2.setImageResource(R.drawable.ic_empty_star)
                holder.itemView.cardview_hos_card_star_img_3.setImageResource(R.drawable.ic_empty_star)
                holder.itemView.cardview_hos_card_star_img_4.setImageResource(R.drawable.ic_empty_star)
                holder.itemView.cardview_hos_card_star_img_5.setImageResource(R.drawable.ic_empty_star)
            }
        }
    }

    private inner class CustomViewHolder (var view : View) : RecyclerView.ViewHolder(view)
}