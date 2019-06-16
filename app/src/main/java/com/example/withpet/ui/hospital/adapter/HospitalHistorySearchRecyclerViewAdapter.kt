package com.example.withpet.ui.hospital.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.withpet.R
import com.example.withpet.util.Log
import com.example.withpet.vo.HospitalSearchDTO
import kotlinx.android.synthetic.main.hospital_search_item.view.*

class HospitalHistorySearchRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var historyList : ArrayList<HospitalSearchDTO> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.hospital_search_item,parent,false)

        return CustomViewHolder(view)
    }

    override fun getItemCount(): Int = historyList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.hospital_item_MainText.text = historyList[position].name.toString()
        holder.itemView.hospital_item_SubText.text = "${historyList[position].gu} ${historyList[position].dong}"
    }

    private inner class CustomViewHolder (var view : View) : RecyclerView.ViewHolder(view)
}