package com.example.withpet.ui.hospital.hospitalMain.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.withpet.R
import com.example.withpet.ui.hospital.hospitalMain.usecase.HistoryRepository
import com.example.withpet.util.Const
import com.example.withpet.vo.hospital.HospitalSearchDTO
import com.example.withpet.vo.eventBus.HospitalCardEventVo
import kotlinx.android.synthetic.main.hospital_search_item.view.*
import org.greenrobot.eventbus.EventBus

class HospitalHistorySearchRecyclerViewAdapter(var repository : HistoryRepository) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var historyList : ArrayList<HospitalSearchDTO> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.hospital_search_item,parent,false)

        return CustomViewHolder(view)
    }

    override fun getItemCount(): Int = historyList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.hospital_item_imageView.setImageResource(R.drawable.ic_history)
        holder.itemView.hospital_item_MainText.text = historyList[position].name.toString()
        holder.itemView.hospital_item_SubText.text = "${historyList[position].gu} ${historyList[position].dong}"

        holder.itemView.hospital_item_layout.setOnClickListener {
            try {
                repository.insertHistory(historyList[position])
                EventBus.getDefault().post(HospitalCardEventVo(Const.SHOW_HOSPITAL_CARDVIEW,historyList[position]))
            }catch (exception : IndexOutOfBoundsException){

            }
        }
    }

    private inner class CustomViewHolder (var view : View) : RecyclerView.ViewHolder(view)
}

/**
 * todo 원균아 에러 좀 봐줘
 * java.lang.IndexOutOfBoundsException: Index: 0, Size: 0
 * at com.example.withpet.ui.hospital.hospitalMain.adapter.HospitalHistorySearchRecyclerViewAdapter$onBindViewHolder$1.onClick(HospitalHistorySearchRecyclerViewAdapter.kt:33)
 * 재현 : 강남 검색 후 검색결과 누르기 -> 앱 죽음
 */
